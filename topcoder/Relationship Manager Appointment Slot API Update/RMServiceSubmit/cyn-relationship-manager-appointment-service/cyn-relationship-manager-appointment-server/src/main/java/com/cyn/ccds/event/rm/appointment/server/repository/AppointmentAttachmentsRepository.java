package com.cyn.ccds.event.rm.appointment.server.repository;

import com.cyn.ccds.event.rm.appointment.server.entity.AppointmentAttachments;
import org.springframework.data.repository.CrudRepository;

/** Repository class for AppointmentAttachments */
public interface AppointmentAttachmentsRepository
    extends CrudRepository<AppointmentAttachments, Long> {}
