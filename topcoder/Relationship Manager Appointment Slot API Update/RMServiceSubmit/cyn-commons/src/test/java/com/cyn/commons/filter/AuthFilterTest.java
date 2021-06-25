package com.cyn.commons.filter;

import static com.cyn.commons.security.SecurityHeaderConstants.ID_TOKEN_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_AUTHORIZATION_HEADER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.util.RSAUtils;
import com.cyn.commons.util.RemoteInvocationService;
import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import java.io.IOException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AuthFilterTest {

  private RSAUtils rsaUtils = new RSAUtils();

  @InjectMocks private AuthFilter authFilter;

  @Mock private MockFilterChain mockFilterChain;

  @Mock private RemoteInvocationService remoteInvocationService;

  AuthFilterTest() throws NoSuchPaddingException, NoSuchAlgorithmException {}

  @BeforeEach
  void setup() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @Test
  void doFilterInternalWithoutAccessToken() throws ServletException, IOException {
    HttpServletRequest request = new MockHttpServletRequest();
    HttpServletResponse response = new MockHttpServletResponse();

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));
  }

  @Test
  void doFilterInternalWithInvalidAccessToken() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_AUTHORIZATION_HEADER, "Bearer");
    HttpServletResponse response = new MockHttpServletResponse();

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));
  }

  @Test
  void doFilterInternal_validAccessToken_invalidIntroResponse()
      throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_AUTHORIZATION_HEADER, "Bearer token");
    HttpServletResponse response = new MockHttpServletResponse();
    GenericResponse<Map> genericResponse = new GenericResponse();
    genericResponse.setBody(
        new HashMap() {
          {
            put("key", "value");
          }
        });
    ResponseEntity<GenericResponse> responseEntity =
        new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    assertNull(auth);

    // inactive idtoken
    genericResponse.setBody(
        new HashMap() {
          {
            put("nbf", 10);
            put("scope", "read");
            put("active", false);
            put("tokenType", "jwt");
            put("exp", 150000000);
            put("iat", 2);
            put("clientId", "topcoder");
            put("username", "topcoder");
            put("customerUniqueIdentifier", "customer");
          }
        });
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());
    authFilter.doFilterInternal(request, response, mockFilterChain);
    auth = SecurityContextHolder.getContext().getAuthentication();
    assertNull(auth);

    // test with null genericResponse
    genericResponse = null;
    responseEntity = new ResponseEntity<>(genericResponse, HttpStatus.OK);
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());
    authFilter.doFilterInternal(request, response, mockFilterChain);
    auth = SecurityContextHolder.getContext().getAuthentication();
    assertNull(auth);

    // remoteService throws exception
    doThrow(new RuntimeException()).when(remoteInvocationService).executeRemoteService(any());
    authFilter.doFilterInternal(request, response, mockFilterChain);
    auth = SecurityContextHolder.getContext().getAuthentication();
    assertNull(auth);
  }

  @Test
  void doFilterInternal_validAccessToken_validIntroResponse_inactiveUser()
      throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_AUTHORIZATION_HEADER, "Bearer token");
    HttpServletResponse response = new MockHttpServletResponse();
    GenericResponse<Map> genericResponse = new GenericResponse();
    genericResponse.setBody(
        new HashMap() {
          {
            put("nbf", 10);
            put("scope", "read");
            put("active", false);
            put("tokenType", "jwt");
            put("exp", 150000000);
            put("iat", 2);
            put("clientId", "topcoder");
            put("username", "topcoder");
          }
        });
    ResponseEntity<GenericResponse> responseEntity =
        new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));
  }

  @Test
  void doFilterInternal_validAccessToken_validIntroResponse_activeUser()
      throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_AUTHORIZATION_HEADER, "Bearer token");
    HttpServletResponse response = new MockHttpServletResponse();
    GenericResponse<Map> genericResponse = new GenericResponse();
    genericResponse.setBody(
        new HashMap() {
          {
            put("nbf", 10);
            put("scope", "read");
            put("active", true);
            put("tokenType", "jwt");
            put("exp", 150000000);
            put("iat", 2);
            put("clientId", "topcoder");
            put("username", "topcoder");
          }
        });
    ResponseEntity<GenericResponse> responseEntity =
        new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));
  }

  @Test
  void doFilterInternal_validAccessToken_validIdToken()
      throws ServletException, IOException, JOSEException {

    KeyPair keyPair = rsaUtils.generateKeyPair();
    String encryptedIdToken = getEncryptedIdToken(keyPair, List.of("admin", "supervisor"));

    ReflectionTestUtils.setField(authFilter, "rolesFieldName", "groups");
    ReflectionTestUtils.setField(
        authFilter,
        "privateKey",
        new String(Base64.getEncoder().encode(keyPair.getPrivate().getEncoded())));
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_AUTHORIZATION_HEADER, "Bearer token");
    request.addHeader(ID_TOKEN_HEADER, encryptedIdToken);
    HttpServletResponse response = new MockHttpServletResponse();
    GenericResponse<Map> genericResponse = new GenericResponse();
    genericResponse.setBody(
        new HashMap() {
          {
            put("nbf", 10);
            put("scope", "read");
            put("active", true);
            put("tokenType", "jwt");
            put("exp", 150000000);
            put("iat", 2);
            put("clientId", "topcoder");
            put("username", "topcoder");
            put("customerUniqueIdentifier", "customer");
          }
        });
    ResponseEntity<GenericResponse> responseEntity =
        new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String principal = (String) auth.getPrincipal();
    assertEquals("[ROLE_admin, ROLE_supervisor]", auth.getAuthorities().toString());
    assertEquals("customer", principal);

    request.removeHeader(ID_TOKEN_HEADER);
    request.addHeader(ID_TOKEN_HEADER, getEncryptedIdToken(keyPair, null));

    authFilter.doFilterInternal(request, response, mockFilterChain);
    auth = SecurityContextHolder.getContext().getAuthentication();
    principal = (String) auth.getPrincipal();
    assertEquals(new ArrayList<>(), auth.getAuthorities());
    assertEquals("customer", principal);

    // forcing parse exception
    request.removeHeader(ID_TOKEN_HEADER);
    request.addHeader(
        ID_TOKEN_HEADER, getEncryptedIdToken(keyPair, List.of("admin", "supervisor")) + "xyz");

    authFilter.doFilterInternal(request, response, mockFilterChain);
    auth = SecurityContextHolder.getContext().getAuthentication();
    principal = (String) auth.getPrincipal();
    assertEquals(new ArrayList<>(), auth.getAuthorities());
    assertEquals("customer", principal);
  }

  private String getEncryptedIdToken(KeyPair keyPair, List<String> roles) throws JOSEException {
    // Compose the JWT claims set
    Date now = new Date();
    JWTClaimsSet jwtClaims =
        new JWTClaimsSet.Builder()
            .issuer("https://openid.net")
            .subject("alice")
            .audience(Arrays.asList("https://app-one.com", "https://app-two.com"))
            .expirationTime(new Date(now.getTime() + 1000 * 60 * 10)) // expires in 10 minutes
            .notBeforeTime(now)
            .claim("groups", roles)
            .issueTime(now)
            .jwtID(UUID.randomUUID().toString())
            .build();

    // Request JWT encrypted with RSA-OAEP-256 and 128-bit AES/GCM
    JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A128GCM);

    // Create the encrypted JWT object
    EncryptedJWT jwt = new EncryptedJWT(header, jwtClaims);

    // Create an encrypter with the specified public RSA key
    RSAEncrypter encrypter = new RSAEncrypter((RSAPublicKey) keyPair.getPublic());

    // Do the actual encryption
    jwt.encrypt(encrypter);

    // Serialise to JWT compact form
    return jwt.serialize();
  }

  @Test
  void doFilterInternal_validAccessToken_blankIdToken() throws ServletException, IOException {
    ReflectionTestUtils.setField(authFilter, "rolesFieldName", "roles");
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_AUTHORIZATION_HEADER, "Bearer token");
    HttpServletResponse response = new MockHttpServletResponse();
    GenericResponse<Map> genericResponse = new GenericResponse();
    genericResponse.setBody(
        new HashMap() {
          {
            put("nbf", 10);
            put("scope", "read");
            put("active", true);
            put("tokenType", "jwt");
            put("exp", 150000000);
            put("iat", 2);
            put("clientId", "topcoder");
            put("username", "topcoder");
            put("customerUniqueIdentifier", "customer");
          }
        });
    ResponseEntity<GenericResponse> responseEntity =
        new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
    doReturn(responseEntity).when(remoteInvocationService).executeRemoteService(any());

    authFilter.doFilterInternal(request, response, mockFilterChain);
    verify(mockFilterChain).doFilter(eq(request), eq(response));

    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String principal = (String) auth.getPrincipal();
    assertEquals("customer", principal);
  }
}
