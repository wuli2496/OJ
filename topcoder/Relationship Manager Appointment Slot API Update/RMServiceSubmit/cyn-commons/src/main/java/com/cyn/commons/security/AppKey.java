package com.cyn.commons.security;

import java.util.UUID;
import lombok.Data;

/** App key DTO used in response. */
@Data
public class AppKey {

  /** App ID. */
  private String appId;

  /** Key GUID. */
  private UUID guid;

  /** Valid from date, in UTC format: yyyy-MM-dd'T'HH:mm:ss */
  private String validFrom;

  /** Valid to date, in UTC format: yyyy-MM-dd'T'HH:mm:ss */
  private String validTo;
}
