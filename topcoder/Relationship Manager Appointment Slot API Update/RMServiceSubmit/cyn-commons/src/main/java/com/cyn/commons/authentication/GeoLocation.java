package com.cyn.commons.authentication;

import lombok.Data;

/** GeoLocation object for login auditing */
@Data
public class GeoLocation {

  /** Latitude of location * */
  private String latitude;

  /** Longitude of location * */
  private String longitude;

  /** Error message id location cannot be determined * */
  private String error;
}
