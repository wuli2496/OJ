package com.cyn.commons.api;

import java.util.Map;
import org.springframework.stereotype.Component;

/** A mapper class to convert between contact points and channels */
@Component
public class ContactPointChannelMapper {
  /** Map contact points to channels */
  private static final Map<String, String> contactPointToChannelMap =
      Map.of("PHONE_ADDRESS", "TEXT MESSAGE", "ELECTRONIC_ADDRESS", "EMAIL");

  /** Map channels to contact points */
  private static final Map<String, String> channelToContactPointMap =
      Map.of("TEXT MESSAGE", "PHONE_ADDRESS", "EMAIL", "ELECTRONIC_ADDRESS");

  /**
   * Return the channel version of given contact point
   *
   * @param contactPoint contact point type
   * @return channel name
   */
  public String mapContactPointTypeToChannel(String contactPoint) {
    return contactPointToChannelMap.get(contactPoint);
  }

  /**
   * Retutn the contact point version of a channel
   *
   * @param channelName channel to map
   * @return contact point type
   */
  public String mapChannelToContactPointType(String channelName) {
    return channelToContactPointMap.get(channelName);
  }
}
