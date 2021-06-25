package com.cyn.commons.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The generic response object for all services as return type
 *
 * @param <T> response type class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse<T> {

  /** The response object * */
  private T body;

  /** The status object * */
  private Status status;

  /**
   * Construct a new Generic response
   *
   * @param body response object/pojo
   */
  public GenericResponse(T body) {
    this.body = body;
  }
}
