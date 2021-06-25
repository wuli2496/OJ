package com.cyn.commons.util;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

/**
 * Structure for making request to another service
 *
 * @param <T> response type
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemoteInvocationContext<T> {
  /** the return type of request * */
  private Class<T> returnType;

  /** the request POJO for the target service* */
  private Object requestBody;

  /** the HTTP Method * */
  private HttpMethod httpMethod;

  /** the url of target service * */
  private String url;

  /** The cyn event to be added as header to request * */
  private String cynEvent;

  /** Whether include internal acess token on header * */
  private boolean includeAccessToken;

  /** Custom headers for the request * */
  private Map<String, String> headers;
}
