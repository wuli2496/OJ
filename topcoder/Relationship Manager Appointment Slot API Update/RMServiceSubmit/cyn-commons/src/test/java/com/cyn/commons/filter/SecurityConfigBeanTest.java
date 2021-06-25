package com.cyn.commons.filter;

import static com.cyn.commons.security.SecurityHeaderConstants.X_APP_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_GUID_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_RHYTHM_HEADER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import com.cyn.commons.exception.ErrorCode;
import com.cyn.commons.security.PayloadEncryption;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@ExtendWith(MockitoExtension.class)
class SecurityConfigBeanTest {

  @InjectMocks private SecurityConfigBean securityConfigBean;

  @Mock private PayloadEncryption payloadEncryption;
  @Mock private Authentication authentication;

  @Test
  void badRequestHandlerWithHeaders() throws IOException, ServletException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    MockHttpServletResponse response = new MockHttpServletResponse();

    AuthException authException = new AuthException("exception", new RuntimeException());

    AuthenticationEntryPoint authenticationEntryPoint =
        securityConfigBean.authenticationEntryPoint();
    authenticationEntryPoint.commence(request, response, authException);
    assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
  }

  @Test
  void accessDeniedHandlerWithHeaders() throws IOException, ServletException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_APP_HEADER, "appHeader");
    request.addHeader(X_GUID_HEADER, "guidHeader");
    request.addHeader(X_RHYTHM_HEADER, "rhythmHeader");

    MockHttpServletResponse response = new MockHttpServletResponse();

    AuthException authException = new AuthException("exception");

    AuthenticationEntryPoint authenticationEntryPoint =
        securityConfigBean.authenticationEntryPoint();
    authenticationEntryPoint.commence(request, response, authException);
    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    verify(payloadEncryption)
        .encryptBodyUsingHeaders(
            any(), anyString(), anyString(), anyString(), any(ServerHttpResponse.class));
  }

  @Test
  void accessDeniedHandlerWithOtherException() throws IOException, ServletException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.addHeader(X_APP_HEADER, "appHeader");
    request.addHeader(X_GUID_HEADER, "guidHeader");
    request.addHeader(X_RHYTHM_HEADER, "rhythmHeader");

    MockHttpServletResponse response = new MockHttpServletResponse();

    AuthenticationException authException =
        new AuthenticationCredentialsNotFoundException("message");

    AuthenticationEntryPoint authenticationEntryPoint =
        securityConfigBean.authenticationEntryPoint();
    authenticationEntryPoint.commence(request, response, authException);
    assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    verify(payloadEncryption)
        .encryptBodyUsingHeaders(
            any(), anyString(), anyString(), anyString(), any(ServerHttpResponse.class));
  }

  @Test
  void corsConfigurationSource() {
    assertNotNull(securityConfigBean.corsConfigurationSource());
  }

  @Test
  void authenticationManager() {
    AuthenticationManager authenticationManager = securityConfigBean.authenticationManager();
    doReturn(false).when(authentication).isAuthenticated();

    try {
      authenticationManager.authenticate(authentication);
    } catch (BadCredentialsException e) {
      assertEquals(ErrorCode.ATH_ERR000004.toString(), e.getMessage());
    }

    doReturn(true).when(authentication).isAuthenticated();

    assertEquals(authentication, authenticationManager.authenticate(authentication));
  }
}
