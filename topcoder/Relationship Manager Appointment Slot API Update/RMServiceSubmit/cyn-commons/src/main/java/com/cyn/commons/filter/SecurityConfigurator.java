package com.cyn.commons.filter;

import static com.cyn.commons.security.SecurityHeaderConstants.X_APP_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_GUID_HEADER;
import static com.cyn.commons.security.SecurityHeaderConstants.X_RHYTHM_HEADER;

import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.api.ResponseCode;
import com.cyn.commons.api.Status;
import com.cyn.commons.exception.ErrorCode;
import com.cyn.commons.security.PayloadEncryption;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

/** Configure the security of application. */
public class SecurityConfigurator extends WebSecurityConfigurerAdapter {

  /** Bundle name for message properties */
  private static final String MESSAGE_BUNDLE_NAME = "messages";

  /** Authenticates all endpoints */
  public static final String DEFAULT_AUTHENTICATES_ALL = "/**";

  /** Actuator health check * */
  private static final String ACTUATOR_HEALTH = "/actuator/health";

  /** Guest public endpoint to generate token */
  private static final String GUEST_TOKEN_GENERATION_ENDPOINT = "/guests/{\\d+}";

  /** Guest public endpoint to start video session */
  private static final String START_VIDEO_SESSION_ENDPOINT = "/guests/{\\d+}/streams";

  /** Auth com.cyn.commons.filter. */
  @Autowired AuthFilter authFilter;

  /** AuthenticationEntryPoint used to handle auth exceptions. */
  @Autowired private AuthenticationEntryPoint authenticationEntryPoint;

  /** The utility to encrypt error structure response */
  @Autowired private PayloadEncryption payloadEncryption;

  /** Flag to enable/disable authentication. Filter get bypassed if it's false */
  @Value("${authentication.enabled:true}")
  private boolean authenticationEnabled;

  /**
   * The method to prepare paths and roles to be assigned.
   *
   * @param expressionInterceptUrlRegistry the expression registry for authentication
   * @return the modified registry
   */
  public ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
      assignPathsAndRoles(
          ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
              expressionInterceptUrlRegistry) {
    if (authenticationEnabled) {
      return expressionInterceptUrlRegistry
          .antMatchers(GUEST_TOKEN_GENERATION_ENDPOINT)
          .permitAll()
          .antMatchers(START_VIDEO_SESSION_ENDPOINT)
          .permitAll()
          .antMatchers(DEFAULT_AUTHENTICATES_ALL)
          .authenticated();
    }
    return expressionInterceptUrlRegistry;
  }

  /** Configure security. */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
        expressionInterceptUrlRegistry =
            http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(ACTUATOR_HEALTH)
                .permitAll();
    assignPathsAndRoles(expressionInterceptUrlRegistry)
        .anyRequest()
        .permitAll()
        .and()
        .logout()
        .disable()
        .exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
  }

  /**
   * A handler/formatter for access denied cases.
   *
   * @return a AccessDeniedHandler bean capable of creating cyn format
   */
  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return (request, response, ex) -> {
      String xAppHeader = request.getHeader(X_APP_HEADER);
      String guidHeader = request.getHeader(X_GUID_HEADER);
      String rhythmHeader = request.getHeader(X_RHYTHM_HEADER);

      if (xAppHeader == null || guidHeader == null || rhythmHeader == null) {
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getOutputStream().flush();
        return;
      }

      ResourceBundle rb = ResourceBundle.getBundle(MESSAGE_BUNDLE_NAME);

      GenericResponse<Object> errorResponse = new GenericResponse<>();
      errorResponse.setStatus(
          Status.builder()
              .code(ResponseCode.FAILURE)
              .errorCode(ErrorCode.ATH_ERR000004.toString())
              .message(
                  rb.containsKey(ErrorCode.ATH_ERR000004.toString())
                      ? rb.getString(ErrorCode.ATH_ERR000004.toString())
                      : ErrorCode.ATH_ERR000004.toString())
              .internalMessage(ex.getMessage())
              .build());
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
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
      response.getOutputStream().flush();
    };
  }
}
