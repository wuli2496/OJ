package com.cyn.ccds.event.rm.appointment.server.service;

import com.cyn.ccds.event.rm.appointment.server.entity.Appointments;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerSlot;
import com.microsoft.graph.models.ChangeNotificationCollection;
import com.microsoft.graph.models.Event;
import java.util.List;

/** Service interface for invoking MS Graph API. */
public interface MicrosoftGraphService {
  /**
   * Create a new event for the given appointment.
   *
   * @param appointment appointment object.
   * @param slots slots.
   * @return Microsoft Graph Event object created.
   */
  Event createEvent(Appointments appointment, List<ManagerSlot> slots);

  /**
   * Update an existing event.
   *
   * @param appointment appointment object.
   * @param slots slots.
   * @return updated event object.
   */
  Event updateEvent(Appointments appointment, List<ManagerSlot> slots);

  /**
   * Delete an event.
   *
   * @param eventId id of the event to delete.
   */
  void deleteEvent(String eventId);

  /**
   * Handles notifications sent from Microsoft Graph.
   *
   * @param notificationCollection the list of notifications.
   */
  void handlesNotifications(ChangeNotificationCollection notificationCollection);
}
