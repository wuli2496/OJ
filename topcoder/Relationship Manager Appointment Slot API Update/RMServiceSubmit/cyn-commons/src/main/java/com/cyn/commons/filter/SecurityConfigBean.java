package com.cyn.commons.filter;

import static com.cyn.commons.security.SecurityHeaderConstants.X_APP_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_GUID_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_RHYTHM_HEADER;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.api.ResponseCode;
import com.cyn.commons.api.Status;
import com.cyn.commons.exception.ErrorCode;
import com.cyn.commons.security.PayloadEncryption;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** Beans related to security config. */
@Slf4j
@Configuration
public class SecurityConfigBean {

  /** Allowed methods for cors filter */
  private static final List<String> ALLOWED_METHODS =
      Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE");

  /** Bundle name for message properties */
  private static final String MESSAGE_BUNDLE_NAME = "messages";

  /** Default path to apply cors filter */
  private static final String DEFAULT_CORS_PATH = "/**";

  /** The utility to encrypt error structure response */
  private final PayloadEncryption payloadEncryption;

  /**
   * Constructor for security config bean
   *
   * @param payloadEncryption response encryption utility
   */
  public SecurityConfigBean(PayloadEncryption payloadEncryption) {
    this.payloadEncryption = payloadEncryption;
  }

  /**
   * Create the AuthenticationEntryPoint bean.
   *
   * @return the AuthenticationEntryPoint
   */
  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPoint() {
      @Override
      public void commence(
          HttpServletRequest request,
          HttpServletResponse response,
          AuthenticationException authException)
          throws IOException {
        String xAppHeader = request.getHeader(X_APP_HEADER);
        String guidHeader = request.getHeader(X_GUID_HEADER);
        String rhythmHeader = request.getHeader(X_RHYTHM_HEADER);

        if (xAppHeader == null || guidHeader == null || rhythmHeader == null) {
          response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
          response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
          response.getOutputStream().flush();
          return;
        }
        GenericResponse<Object> errorResponse = new GenericResponse<>();
        ResourceBundle rb = ResourceBundle.getBundle(MESSAGE_BUNDLE_NAME);

        if (authException instanceof AuthException) {
          var code = ((AuthException) authException).getCode();
          errorResponse.setStatus(
              Status.builder()
                  .code(ResponseCode.FAILURE)
                  .errorCode(code)
                  .message(rb.containsKey(code) ? rb.getString(code) : code)
                  .internalMessage(authException.getMessage())
                  .build());
        } else {
          errorResponse.setStatus(
              Status.builder()
                  .code(ResponseCode.FAILURE)
                  .errorCode(ErrorCode.ATH_ERR000004.toString())
                  .message(
                      rb.containsKey(ErrorCode.ATH_ERR000004.toString())
                          ? rb.getString(ErrorCode.ATH_ERR000004.toString())
                          : ErrorCode.ATH_ERR000004.toString())
                  .internalMessage(authException.getMessage())
                  .build());
        }
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response
            .getOutputStream()
            .println(
                (String)
                    payloadEncryption.encryptBodyUsingHeaders(
                        errorResponse,
                        xAppHeader,
                        guidHeader,
                        rhythmHeader,
                        new ServletServerHttpResponse(response)));
      }
    };
  }

  /**
   * Create the CorsConfigurationSource bean.
   *
   * @return the CorsConfigurationSource
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    log.info("Config web security for CORS");
    var source = new UrlBasedCorsConfigurationSource();
    var corsConfig = new CorsConfiguration();
    corsConfig.setAllowedMethods(ALLOWED_METHODS);
    corsConfig.applyPermitDefaultValues();
    source.registerCorsConfiguration(DEFAULT_CORS_PATH, corsConfig);
    return source;
  }

  /**
   * Create the AuthenticationManager bean.
   *
   * @return the AuthenticationManager
   */
  @Bean
  public AuthenticationManager authenticationManager() {
    return authentication -> {
      if (!authentication.isAuthenticated()) {
        throw new BadCredentialsException(ErrorCode.ATH_ERR000004.toString());
      }
      return authentication;
    };
  }
}
