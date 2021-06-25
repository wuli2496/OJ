package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.api.Status;
import com.cyn.commons.authentication.LoginResponse;
import com.cyn.commons.filter.TokenPair;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AuthenticationUtilsTest {

  private AuthenticationUtils authenticationUtils =
      new AuthenticationUtils("server", "username", "password");

  @Test
  void getAccessTokenRequest() {
    assertNotNull(authenticationUtils.getAccessTokenRequest());
  }

  @Test
  void getAuthenticationServerSystemLoginUrl() {
    assertEquals(
        "http://server/v1/security/service/authenticate/system/login",
        authenticationUtils.getAuthenticationServerSystemLoginUrl());
  }

  @Test
  void extractAccessToken() {
    GenericResponse<LoginResponse> loginResponse = new GenericResponse<>();
    loginResponse.setBody(new LoginResponse());
    loginResponse.setStatus(Status.builder().build());
    loginResponse.getBody().setAccessToken("accessToken");
    loginResponse.getBody().setAccessTokenExpiresIn(200L);
    loginResponse.getBody().setIdToken("idToken");
    loginResponse.getBody().setRefreshToken("refreshToken");
    loginResponse.getBody().setRefreshTokenExpiresIn(20L);
    loginResponse.getBody().setScope("scope");
    loginResponse.getBody().setTokenType("jwt");

    assertEquals(
        TokenPair.builder().accessToken("accessToken").idToken("idToken").build(),
        authenticationUtils.extractTokens(new ResponseEntity<>(loginResponse, HttpStatus.OK)));
  }
}
