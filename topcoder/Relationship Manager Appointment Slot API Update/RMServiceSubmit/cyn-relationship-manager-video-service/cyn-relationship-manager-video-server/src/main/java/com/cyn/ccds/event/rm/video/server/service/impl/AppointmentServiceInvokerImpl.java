package com.cyn.ccds.event.rm.video.server.service.impl;

import com.cyn.ccds.event.rm.appointment.client.AppointmentDTO;
import com.cyn.ccds.event.rm.video.server.exception.ErrorCode;
import com.cyn.ccds.event.rm.video.server.service.AppointmentServiceInvoker;
import com.cyn.commons.api.GenericResponse;
import com.cyn.commons.exception.BaseServiceException;
import com.cyn.commons.util.JsonUtils;
import com.cyn.commons.util.RemoteInvocationContext;
import com.cyn.commons.util.RemoteInvocationService;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/** Implementation class for {@link AppointmentServiceInvoker} */
@Slf4j
@RequiredArgsConstructor
@Component
public class AppointmentServiceInvokerImpl implements AppointmentServiceInvoker {
  /** Cyn event to be used in the http message */
  public static final String CYN_EVENT = "APPOINTMENT_MESSAGE";

  /** rm-appointment server url. */
  private static final String APPOINTMENT_RETRIEVE_URL =
      "http://%s/v1/appointments/service/customer/appointments/%d";

  /** Return type of the appointment service. */
  private static final TypeReference<GenericResponse<AppointmentDTO>> RESPONSE_TYPE =
      new TypeReference<>() {};

  /** Remote invocation service. Injected via constructor. */
  private final RemoteInvocationService remoteInvocationService;

  /** rm-appointment server name */
  @Value("${appointment.server.name}")
  private String appointmentServerName;

  /**
   * Invoke the RM appointment service to get appointment info.
   *
   * @param appointmentId id of the appointment to get.
   * @return Appointment details.
   */
  @Override
  public AppointmentDTO getAppointment(long appointmentId) {
    RemoteInvocationContext<?> context =
        RemoteInvocationContext.builder()
            .cynEvent(CYN_EVENT)
            .returnType(Object.class)
            .httpMethod(HttpMethod.GET)
            .url(String.format(APPOINTMENT_RETRIEVE_URL, appointmentServerName, appointmentId))
            .includeAccessToken(true)
            .build();
    try {
      ResponseEntity<?> responseEntity = remoteInvocationService.executeRemoteService(context);
      return JsonUtils.OBJECT_MAPPER
          .convertValue(responseEntity.getBody(), RESPONSE_TYPE)
          .getBody();
    } catch (Exception e) {
      log.error("Failed to call appointment service.", e);
      throw new BaseServiceException(ErrorCode.RMV_ERR000010.name(), Collections.emptyList());
    }
  }
}
