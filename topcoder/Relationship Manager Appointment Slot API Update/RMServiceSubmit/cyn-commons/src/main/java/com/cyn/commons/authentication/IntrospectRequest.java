package com.cyn.commons.authentication;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Token introspect request */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntrospectRequest {

  /** Token to introspect * */
  @NotNull private String token;
}
