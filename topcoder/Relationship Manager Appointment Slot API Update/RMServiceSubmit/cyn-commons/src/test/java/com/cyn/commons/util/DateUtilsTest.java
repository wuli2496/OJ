package com.cyn.commons.util;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;

class DateUtilsTest {

  @Test
  void init() {
    LocalDateTime date = LocalDateTime.parse("2013-10-22T01:37:56");

    String dateStr = DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneOffset.UTC).format(date);
    assertEquals("2013-10-22", dateStr);

    dateStr = DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC).format(date);
    assertEquals("2013-10-22T01:37:56", dateStr);
  }
}
