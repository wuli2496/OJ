package com.cyn.ccds.event.rm.video.server.filter;

import com.cyn.ccds.event.rm.video.server.service.RmVideoService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.util.Objects;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** Filter to handle OpenVidu webhook event requests. */
@RequiredArgsConstructor
@Slf4j
@Component
@Scope("singleton")
@Order(value = Integer.MIN_VALUE)
public class OpenviduWebhookFilter extends OncePerRequestFilter {
  /** API token prefix. */
  private static final String TOKEN_PREFIX = "bearer ";

  /** Message sent to client on authorization error. */
  private static final String UNAUTHORIZED_REQUEST = "Unauthorized request";

  /** Message sent to client on unsupported method. */
  private static final String UNSUPPORTED_METHOD = "Unsupported method";

  /** Object mapper to parse the event json, ignoring unknown properties. */
  private static final ObjectMapper OBJECT_MAPPER =
      new Jackson2ObjectMapperBuilder()
          .featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
          .build();

  /** Request path the webhook filter listens on. */
  @Value("${openvidu.webhook.path}")
  private String openviduWebhookPath;

  /** Web API token the OpenVidu server should send in the http header. */
  @Value("${openvidu.webhook.token}")
  private String openviduWebhookApiToken;

  /** RM video service. */
  private final RmVideoService rmVideoServiceImpl;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if ((request.getContextPath() + openviduWebhookPath).equals(request.getRequestURI())) {
      handleOpenviduEvent(request, response);
    } else {
      filterChain.doFilter(request, response);
    }
  }

  /**
   * Handles Open Vidu webhook requests.
   *
   * @param request http request.
   * @param response http response.
   */
  private void handleOpenviduEvent(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    if (!HttpMethod.POST.matches(request.getMethod())) {
      log.warn("Unsupported HTTP method {}", request.getMethod());
      sendResponse(HttpStatus.METHOD_NOT_ALLOWED, UNSUPPORTED_METHOD, response);
      return;
    }
    if (!checkApiToken(request)) {
      log.warn("Unauthenticated webhook request.");
      sendResponse(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_REQUEST, response);
      return;
    }

    String body = new String(ByteStreams.toByteArray(request.getInputStream()));
    log.debug("OpenVidu event {}", body);
    OpenviduEvent event = OBJECT_MAPPER.readValue(body, OpenviduEvent.class);
    if (event.isSessionDestroyed()) {
      log.info("Session destroyed event received: {}", event.getSessionId());
      rmVideoServiceImpl.onDestroySession(event.getSessionId());
    }

    sendResponse(HttpStatus.OK, null, response);
  }

  /**
   * Check API token in the request authorization header.
   *
   * @param request the http request.
   * @return true if token exists and equals the configured token.
   */
  private boolean checkApiToken(HttpServletRequest request) {
    String auth = request.getHeader("Authorization");
    if (auth == null || !auth.toLowerCase().startsWith(TOKEN_PREFIX)) {
      return false;
    }
    String token = auth.substring(TOKEN_PREFIX.length());
    return Objects.equals(token, openviduWebhookApiToken);
  }

  /**
   * Send HTTP code and message to the client.
   *
   * @param httpStatus http status code.
   * @param msg message.
   * @param response http response to send the message to.
   * @throws IOException if IO error happens.
   */
  private void sendResponse(HttpStatus httpStatus, String msg, HttpServletResponse response)
      throws IOException {
    response.setStatus(httpStatus.value());
    if (msg != null) {
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      String message = String.format("{\"message\":\"%s\"}", msg);
      response.setContentLength(message.length());
      response.getWriter().write(message);
    }
  }
}
