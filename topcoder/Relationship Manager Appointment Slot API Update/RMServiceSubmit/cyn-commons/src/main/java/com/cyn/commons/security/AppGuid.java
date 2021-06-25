package com.cyn.commons.security;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** App ID and Key GUID. */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppGuid {
  /** App ID. */
  @NotBlank private String appId;

  /** Key GUID. */
  private UUID guid;
}
