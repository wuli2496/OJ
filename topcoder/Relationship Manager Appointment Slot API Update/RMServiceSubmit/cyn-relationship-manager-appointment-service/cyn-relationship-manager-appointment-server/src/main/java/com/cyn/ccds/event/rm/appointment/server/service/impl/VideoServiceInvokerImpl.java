package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.client.SessionDTO;
import com.cyn.ccds.event.rm.appointment.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.appointment.server.service.VideoServiceInvoker;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.util.JsonUtils;
import com.cyn.commons.util.RemoteInvocationContext;
import com.cyn.commons.util.RemoteInvocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/** Implementation class for {@link VideoServiceInvoker} */
@Service
public class VideoServiceInvokerImpl implements VideoServiceInvoker {

  /** Cyn event to be used in http request */
  public static final String CYN_EVENT = "SESSION_MESSAGE";

  /** rm-appointment server url. */
  private static final String VIDEO_RETRIEVE_URL =
      "http://%s/v1/conferencing/service/customer/rm/sessions";

  /** Return type of email channel */
  private static final TypeReference<GenericResponse<SessionDTO>> GENERIC_TYPE =
      new TypeReference<>() {};

  /** Auto injected RemoteInvocationService instance */
  @Autowired private RemoteInvocationService invocationService;

  /** rm-video server host */
  @Value("${video.server.name}")
  private String videoServerName;

  /**
   * Create a session in rm-video server
   *
   * @param appointmentId the appointmentId
   * @param managerName the name of the manager
   * @param userName the name of the appointment creator
   * @return the created sessionId
   */
  public String createSession(Long appointmentId, String managerName, String userName) {
    Map<String, String> body = new HashMap<>();
    body.put("appointmentId", appointmentId.toString());
    body.put("managerName", managerName);
    body.put("userName", userName);
    RemoteInvocationContext<?> context =
        RemoteInvocationContext.builder()
            .cynEvent(CYN_EVENT)
            .returnType(Object.class)
            .requestBody(body)
            .httpMethod(HttpMethod.POST)
            .url(String.format(VIDEO_RETRIEVE_URL, videoServerName))
            .includeAccessToken(Boolean.TRUE)
            .build();
    try {
      ResponseEntity<?> responseEntity = invocationService.executeRemoteService(context);
      return JsonUtils.OBJECT_MAPPER
          .convertValue(responseEntity.getBody(), GENERIC_TYPE)
          .getBody()
          .getToken();
    } catch (Exception e) {
      throw new BaseServiceException(ErrorCode.RMA_ERR000012.name(), Collections.emptyList());
    }
  }
}
