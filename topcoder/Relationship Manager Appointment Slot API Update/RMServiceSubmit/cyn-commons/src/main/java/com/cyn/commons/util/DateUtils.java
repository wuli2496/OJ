package com.cyn.commons.util;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/** Utility to define date formatters */
public class DateUtils {

  /** Private constructor for utility method to prevent inheritance */
  private DateUtils() {}

  /** The date formatter with UTC zone, format: yyyy-MM-dd */
  public static final DateTimeFormatter UTC_DATE_FORMATTER =
      DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneOffset.UTC);

  /** The date time formatter with UTC zone, format: yyyy-MM-dd'T'HH:mm:ss */
  public static final DateTimeFormatter UTC_DATETIME_FORMATTER =
      DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC);
}
