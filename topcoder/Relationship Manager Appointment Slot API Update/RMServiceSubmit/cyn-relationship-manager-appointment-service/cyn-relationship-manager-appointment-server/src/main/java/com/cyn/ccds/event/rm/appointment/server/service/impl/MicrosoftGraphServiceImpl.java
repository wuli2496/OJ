package com.cyn.ccds.event.rm.appointment.server.service.impl;

import com.cyn.ccds.event.rm.appointment.server.entity.Appointments;
import com.cyn.ccds.event.rm.appointment.server.entity.ManagerSlot;
import com.cyn.ccds.event.rm.appointment.server.service.MicrosoftGraphService;
import com.microsoft.graph.http.GraphServiceException;
import com.microsoft.graph.models.Attendee;
import com.microsoft.graph.models.AttendeeType;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.ChangeNotification;
import com.microsoft.graph.models.ChangeNotificationCollection;
import com.microsoft.graph.models.ChangeType;
import com.microsoft.graph.models.DateTimeTimeZone;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.Event;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Location;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.Subscription;
import com.microsoft.graph.requests.EventCollectionRequest;
import com.microsoft.graph.requests.GraphServiceClient;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/** Implementation of the microsoft graph service interface. */
@RequiredArgsConstructor
@Slf4j
@Service
public class MicrosoftGraphServiceImpl implements MicrosoftGraphService {
  /** Microsoft Graph Client reference. */
  private final GraphServiceClient<Request> client;

  /** Task executor. */
  private final TaskExecutor taskExecutor;

  /** Organizer user id. */
  @Value("${microsoft.graph.calendar.user}")
  private String calendarUserId;

  /** Calendar id. */
  @Value("${microsoft.graph.calendar.id}")
  private String calendarId;

  /** Base URL for receiving microsoft graph notifications. */
  @Value("${microsoft.graph.notification.base.url}")
  private String subscriptionBaseUrl;

  /** Servlet context path */
  @Value("${server.servlet.context-path}")
  private String contextPath;

  /** Notification path. */
  @Value("${microsoft.graph.notification.path}")
  private String notificationPath;

  /** Notification subscription expiration in minutes. */
  @Value("${microsoft.graph.notification.expiration.minutes}")
  private long subscriptionExpirationMinutes;

  /** Client state passed to the subscription api. */
  @Value("${microsoft.graph.notification.client.state}")
  private String clientState;

  /** Scheduled task to create/update subscription. */
  @Scheduled(initialDelay = 1000, fixedDelay = 3600L * 1000)
  public void updateSubscription() {
    if (StringUtils.isBlank(notificationPath)) {
      log.warn("Notification path is blank, skip subscription setup.");
      return;
    }

    try {
      String resource = String.format("/users/%s/calendars/%s/events", calendarUserId, calendarId);
      var subscription = getSubscriptionFor(resource);
      if (subscription == null) {
        log.info("No subscription found for resource {}, creating new.", resource);
        subscription = new Subscription();
        subscription.resource = resource;
        subscription.changeType = "updated,deleted";
        subscription.clientState = clientState;
        subscription.notificationUrl =
            String.format("%s%s%s", subscriptionBaseUrl, contextPath, notificationPath);
        subscription.expirationDateTime =
            OffsetDateTime.now().plusMinutes(subscriptionExpirationMinutes);
        subscription = client.subscriptions().buildRequest().post(subscription);
        log.info("Successfully created subscription {}", subscription.id);
      } else {
        var update = new Subscription();
        update.expirationDateTime = OffsetDateTime.now().plusMinutes(subscriptionExpirationMinutes);
        log.info("Updating subscription expiration to {}", update.expirationDateTime);
        client.subscriptions(subscription.id).buildRequest().patch(update);
        log.info("Successfully updated subscription {}", subscription.id);
      }
    } catch (Exception e) {
      log.error("Failed subscription", e);
    }
  }

  @Override
  public Event createEvent(Appointments appointment, List<ManagerSlot> slots) {
    Event event = getEvent(appointment, slots);
    return getEventCollectionRequest().post(event);
  }

  @Override
  public Event updateEvent(Appointments appointment, List<ManagerSlot> slots) {
    return client
        .users(calendarUserId)
        .calendars(calendarId)
        .events(appointment.getMicrosoftGraphEventId())
        .buildRequest()
        .patch(getEvent(appointment, slots));
  }

  @Override
  public void deleteEvent(String eventId) {
    client.users(calendarUserId).calendars(calendarId).events(eventId).buildRequest().delete();
    log.info("Successfully deleted event {}", eventId);
  }

  @Override
  public void handlesNotifications(ChangeNotificationCollection notificationCollection) {
    taskExecutor.execute(
        () -> {
          for (ChangeNotification changeNotification : notificationCollection.value) {
            if (!clientState.equals(changeNotification.clientState)) {
              log.warn(
                  "Incoming client state {} does not match {}",
                  changeNotification.clientState,
                  clientState);
              break;
            }
            log.info("Handles notification for resource {}", changeNotification.resource);
            String eventId =
                changeNotification.resourceData.additionalDataManager().get("id").toString();
            if (changeNotification.changeType == ChangeType.DELETED) {
              log.info("Event deleted: {}", eventId);
            } else {
              try {
                Event event =
                    client
                        .users(calendarUserId)
                        .calendars(calendarId)
                        .events(eventId)
                        .buildRequest()
                        .get();
                if (event.transactionId.startsWith("rm-appointment-")) {
                  log.info("Ignore event change notification from this service.");
                }
              } catch (GraphServiceException e) {
                log.warn("Failed to get event {}", eventId, e);
              }
            }
          }
        });
  }

  /**
   * Get event from appointment.
   *
   * @param appointment appointment
   * @param slots slots
   * @return event instance.
   */
  private Event getEvent(Appointments appointment, List<ManagerSlot> slots) {
    Event event = new Event();
    event.subject = appointment.getSubject();
    event.body = new ItemBody();
    event.body.content = appointment.getDescription();
    event.body.contentType = BodyType.HTML;
    event.start = new DateTimeTimeZone();
    event.start.dateTime =
        appointment
            .getAppointmentDate()
            .toLocalDate()
            .atTime(LocalTime.ofSecondOfDay(slots.get(0).getStartTime()))
            .format(DateTimeFormatter.ISO_DATE_TIME);
    event.start.timeZone = appointment.getAppointmentDate().getZone().getId();
    event.end = new DateTimeTimeZone();
    event.end.dateTime =
        appointment
            .getAppointmentDate()
            .toLocalDate()
            .atTime(LocalTime.ofSecondOfDay(slots.get(slots.size() - 1).getEndTime()))
            .format(DateTimeFormatter.ISO_DATE_TIME);
    event.end.timeZone = appointment.getAppointmentDate().getZone().getId();
    event.location = new Location();
    event.location.displayName = appointment.getAddress();
    event.transactionId = getTransactionId(appointment);
    event.attendees = getAttendees(appointment);
    event.organizer = getOrganizer();
    event.allowNewTimeProposals = true;
    return event;
  }

  /**
   * Get an instance of {@link EventCollectionRequest}.
   *
   * @return an EventCollectionRequest object.
   */
  private EventCollectionRequest getEventCollectionRequest() {
    return client.users(calendarUserId).calendars(calendarId).events().buildRequest();
  }

  /**
   * Get transaction id for the given appointment.
   *
   * @param appointment appointment
   * @return a transaction id
   */
  private String getTransactionId(Appointments appointment) {
    return String.format("rm-appointment-%d", appointment.getId());
  }

  /**
   * Get a list of attendees from the appointment object.
   *
   * @param appointment the appointment object
   * @return a list of attendees that contains the user and manager.
   */
  private List<Attendee> getAttendees(Appointments appointment) {
    return Stream.of(appointment.getUserName(), appointment.getManagerName())
        .map(
            name -> {
              Attendee attendee = new Attendee();
              attendee.emailAddress = newEmailAddress(getEmail(name), name);
              attendee.type = AttendeeType.REQUIRED;
              return attendee;
            })
        .collect(Collectors.toList());
  }

  /**
   * Get user email.
   *
   * @return the email
   */
  private String getEmail(String name) {
    if (name.equals("93129238")) {
      return "AdeleV@pvmagachotc.onmicrosoft.com";
    }
    return "AlexW@pvmagachotc.onmicrosoft.com";
  }

  /**
   * Get the event organizer.
   *
   * @return event organizer.
   */
  private Recipient getOrganizer() {
    Recipient organizer = new Recipient();
    organizer.emailAddress = newEmailAddress(calendarUserId, null);
    return organizer;
  }

  /**
   * Constructs a new {@link EmailAddress} instance from the given address and name.
   *
   * @param address email address.
   * @param name name.
   * @return an instance of {@link EmailAddress}
   */
  private EmailAddress newEmailAddress(String address, String name) {
    EmailAddress addr = new EmailAddress();
    addr.address = address;
    addr.name = name;
    return addr;
  }

  /**
   * Get change subscription for the given resource.
   *
   * @param resource - resource to filter the subscription.
   * @return the existing subscription or null.
   */
  @Nullable
  private Subscription getSubscriptionFor(String resource) {
    var request = client.subscriptions().buildRequest();
    while (request != null) {
      var page = request.get();
      if (page == null) {
        break;
      }
      for (Subscription subscription : page.getCurrentPage()) {
        if (resource.equals(subscription.resource)) {
          log.info("found existing subscription {} for resource {}", subscription, resource);
          return subscription;
        }
      }
      if (page.getNextPage() == null) {
        break;
      }
      request = page.getNextPage().buildRequest();
    }
    return null;
  }
}
