package com.cyn.commons.util;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.authentication.Credentials;
import com.cyn.commons.authentication.LoginRequest;
import com.cyn.commons.authentication.LoginResponse;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.exception.ErrorCode;
import com.cyn.commons.filter.TokenPair;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtils {
  // The response type for Login response
  private static final TypeReference<GenericResponse<LoginResponse>> LOGIN_RESP_TYPE =
      new TypeReference<>() {};

  // Authentication endpoint for internal services in authentication service
  private static final String AUTHENTICATION_SERVER_SYSTEM_LOGIN_URL =
      "http://%s/v1/security/service/authenticate/system/login";
  // The endpoint/service name of authentication service
  private final String authenticationServer;

  // The username to use for system login
  private final String serviceUsername;

  // The password to use for system login
  private final String servicePassword;

  /**
   * Consturctor to be called by Spring
   *
   * @param authenticationServer authentication service/hostname
   * @param serviceUsername The username to use for system login
   * @param servicePassword The password to use for system login
   */
  public AuthenticationUtils(
      @Value("${authentication.server.name}") String authenticationServer,
      @Value("${internal.service.user}") String serviceUsername,
      @Value("${internal.service.password}") String servicePassword) {
    this.authenticationServer = authenticationServer;
    this.serviceUsername = serviceUsername;
    this.servicePassword = servicePassword;
  }

  /**
   * This method will fetch the token from authentication service using a pre configured username
   * and password.
   *
   * @return login request object
   */
  public LoginRequest getAccessTokenRequest() {
    try {
      return LoginRequest.builder()
          .credentials(
              Credentials.builder().password(servicePassword).userName(serviceUsername).build())
          .build();
    } catch (Exception e) {
      throw new BaseServiceException(
          ErrorCode.ATH_ERR000005.toString(), Collections.emptyList(), e);
    }
  }

  /**
   * Get url of system login request
   *
   * @return the url of the service containing service name
   */
  public String getAuthenticationServerSystemLoginUrl() {
    return String.format(AUTHENTICATION_SERVER_SYSTEM_LOGIN_URL, authenticationServer);
  }

  /**
   * Extract tokens from response
   *
   * @return the wso access token and id token
   */
  public TokenPair extractTokens(ResponseEntity<Object> responseEntity) {
    LoginResponse loginResponse =
        JsonUtils.OBJECT_MAPPER.convertValue(responseEntity.getBody(), LOGIN_RESP_TYPE).getBody();
    return TokenPair.builder()
        .idToken(loginResponse.getIdToken())
        .accessToken(loginResponse.getAccessToken())
        .build();
  }
}
