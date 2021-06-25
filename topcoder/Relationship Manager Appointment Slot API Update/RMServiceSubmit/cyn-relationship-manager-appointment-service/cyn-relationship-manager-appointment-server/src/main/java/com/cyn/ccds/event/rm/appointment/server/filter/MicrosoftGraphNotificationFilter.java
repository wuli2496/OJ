package com.cyn.ccds.event.rm.appointment.server.filter;

import com.cyn.ccds.event.rm.appointment.server.service.MicrosoftGraphService;
import com.google.common.io.ByteStreams;
import com.microsoft.graph.logger.DefaultLogger;
import com.microsoft.graph.models.ChangeNotificationCollection;
import com.microsoft.graph.serializer.DefaultSerializer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/** A filter that handles the Microsoft Graph Notification events. */
@RequiredArgsConstructor
@Slf4j
@Component
@Order(Integer.MIN_VALUE)
public class MicrosoftGraphNotificationFilter extends OncePerRequestFilter {
  /** Microsoft Graph service reference. */
  private final MicrosoftGraphService microsoftGraphService;

  /** Notification path from configuration. */
  @Value("${microsoft.graph.notification.path}")
  private String notificationPath;

  /**
   * Implements the doFilterInternal method.
   *
   * <p>Same contract as for doFilter, but guaranteed to be just invoked once per request within a
   * single request thread. See shouldNotFilterAsyncDispatch() for details. Provides
   * HttpServletRequest and HttpServletResponse arguments instead of the default ServletRequest and
   * ServletResponse ones.
   *
   * @param request http request object.
   * @param response http response object.
   * @param filterChain filter chain.
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doFilterInternal(
      @NotNull HttpServletRequest request,
      @NotNull HttpServletResponse response,
      @NotNull FilterChain filterChain)
      throws ServletException, IOException {
    handleNotification(request, response);
  }

  /**
   * Do not filter requests that do not have the expected URI.
   *
   * @param request http request object.
   * @return true if request URI is not equal to {contextPath}{notificationPath}.
   */
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return !(request.getContextPath() + notificationPath).equals(request.getRequestURI());
  }

  /**
   * Handles the notification request.
   *
   * @param request http request.
   * @param response http response.
   * @throws IOException if IO error happens
   */
  private void handleNotification(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String validationToken = request.getParameter("validationToken");
    if (validationToken != null) {
      log.info("Handles URL validation request: {}", validationToken);
      response.getWriter().print(validationToken);
    } else {
      byte[] bytes = ByteStreams.toByteArray(request.getInputStream());
      String body = new String(bytes, StandardCharsets.UTF_8);

      DefaultSerializer defaultSerializer = new DefaultSerializer(new DefaultLogger());
      ChangeNotificationCollection notifications =
          defaultSerializer.deserializeObject(body, ChangeNotificationCollection.class);
      if (notifications != null && notifications.value != null) {
        microsoftGraphService.handlesNotifications(notifications);
      }

      response.setStatus(202);
    }
  }
}
