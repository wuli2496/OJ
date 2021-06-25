package com.cyn.commons.filter;

import static com.cyn.commons.security.SecurityHeaderConstants.ID_TOKEN_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.STANDARD_AUTHORIZATION_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_AUTHORIZATION_HEADER;
import static org.apache.logging.log4j.util.Strings.isBlank;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.authentication.IntrospectRequest;
import com.cyn.commons.authentication.IntrospectResponse;
import com.cyn.commons.util.JsonUtils;
import com.cyn.commons.util.RemoteInvocationContext;
import com.cyn.commons.util.RemoteInvocationService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jwt.EncryptedJWT;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Auth com.cyn.commons.filter. */
@Slf4j
@Component
@Scope("singleton")
public class AuthFilter extends OncePerRequestFilter {

  /** Bearer token prefix * */
  private static final String BEARER_TOKEN = "Bearer";

  /** CYN Event for calling introspect endpoint * */
  private static final String SECURITY_EVENT_INTROSPECT = "SECURITY_EVENT:INTROSPECT";

  /** Authentication service introspect url * */
  private static final String AUTHENTICATE_INTROSPECT_URL =
      "http://%s/v1/security/service/authenticate/introspect";

  /** Prefix for spring role */
  private static final String SPRING_ROLE_PREFIX = "ROLE_";

  /** Rsa algorithm instance */
  private static final String RSA_ALGORITHM = "RSA";

  /** Server name of authentication service. */
  @Value("${authentication.server.name}")
  private String authServerName;

  /** The roles field in id_token. */
  @Value("${roles.field.name:groups}")
  private String rolesFieldName;

  /** Private key of this service * */
  @Value("${id.token.issuer.private.key}")
  private String privateKey;

  /** AuthenticationEntryPoint used to handle auth exceptions. */
  @Autowired private AuthenticationEntryPoint authenticationEntryPoint;

  /** Remote invocation service. */
  @Autowired private RemoteInvocationService remoteInvocationService;

  /** Use authentication service to introspect the access token. */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String accessToken =
        request.getHeader(X_AUTHORIZATION_HEADER) == null
            ? request.getHeader(STANDARD_AUTHORIZATION_HEADER)
            : request.getHeader(X_AUTHORIZATION_HEADER);

    if (isBlank(accessToken)) {
      filterChain.doFilter(request, response);
      return;
    }

    if (accessToken.contains(BEARER_TOKEN)) {
      String[] bearerTokenContent = accessToken.split(" ");
      if (bearerTokenContent.length < 2) {
        filterChain.doFilter(request, response);
        return;
      }
      accessToken = bearerTokenContent[1];
    }

    GenericResponse<?> genericResponse;
    try {
      genericResponse =
          remoteInvocationService
              .executeRemoteService(
                  new RemoteInvocationContext<>(
                      GenericResponse.class,
                      new IntrospectRequest(accessToken),
                      HttpMethod.POST,
                      String.format(AUTHENTICATE_INTROSPECT_URL, authServerName),
                      SECURITY_EVENT_INTROSPECT,
                      Boolean.FALSE,
                      Map.of()))
              .getBody();
    } catch (Exception ex) {
      filterChain.doFilter(request, response);
      return;
    }

    var introResponse =
        genericResponse == null
            ? null
            : JsonUtils.OBJECT_MAPPER.convertValue(
                genericResponse.getBody(), IntrospectResponse.class);
    if (introResponse == null
        || isBlank(introResponse.getUsername())
        || isBlank(introResponse.getCustomerUniqueIdentifier())) {
      // Invalid access token
      filterChain.doFilter(request, response);
      return;
    }

    if (!introResponse.isActive()) {
      // User not active
      filterChain.doFilter(request, response);
      return;
    }

    String idToken = request.getHeader(ID_TOKEN_HEADER);
    String customerId = introResponse.getCustomerUniqueIdentifier();
    createSecurityContext(accessToken, idToken, customerId);

    filterChain.doFilter(request, response);
  }

  /**
   * Internal helper method for creating security context from request headers.
   *
   * @param accessToken the access token from header.
   * @param idToken the id token containing roles.
   * @param customerId unique customer id.
   */
  private void createSecurityContext(String accessToken, String idToken, String customerId) {
    if (isBlank(idToken)) {
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
              customerId,
              TokenPair.builder().accessToken(accessToken).build(),
              Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(auth);
    } else {
      List<String> roles = getRolesFromClaims(idToken);
      List<SimpleGrantedAuthority> simpleGrantedAuthorities;
      if (roles != null) {
        simpleGrantedAuthorities =
            roles.stream().map(this::mapToSpringRole).collect(Collectors.toList());
      } else {
        simpleGrantedAuthorities = Collections.emptyList();
      }
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
              customerId,
              TokenPair.builder().accessToken(accessToken).idToken(idToken).build(),
              simpleGrantedAuthorities);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }
  }

  /**
   * Get list of roles from encrypted id token.
   *
   * @param encryptedToken encrypted id token.
   * @return list of roles.
   */
  private List<String> getRolesFromClaims(String encryptedToken) {
    try {
      EncryptedJWT jwt = EncryptedJWT.parse(encryptedToken);
      RSADecrypter decryptor = new RSADecrypter(getPrivateKey(privateKey));
      jwt.decrypt(decryptor);
      return jwt.getJWTClaimsSet().getStringListClaim(rolesFieldName);
    } catch (ParseException
        | JOSEException
        | NoSuchAlgorithmException
        | InvalidKeySpecException e) {
      return Collections.emptyList();
    }
  }

  /**
   * Retrieve private key from encoded private key.
   *
   * @param encodedPrivateKey base64 encoded private key.
   * @return PrivateKey instance.
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  private PrivateKey getPrivateKey(String encodedPrivateKey)
      throws NoSuchAlgorithmException, InvalidKeySpecException {
    byte[] decoded = Base64.getDecoder().decode(encodedPrivateKey);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
    KeyFactory kf = KeyFactory.getInstance(RSA_ALGORITHM);
    return kf.generatePrivate(keySpec);
  }

  /**
   * Map a id token role to spring role.
   *
   * @param role the id token role.
   * @return spring SimpleGrantedAuthority.
   */
  private SimpleGrantedAuthority mapToSpringRole(String role) {
    return new SimpleGrantedAuthority(SPRING_ROLE_PREFIX.concat(role));
  }
}
