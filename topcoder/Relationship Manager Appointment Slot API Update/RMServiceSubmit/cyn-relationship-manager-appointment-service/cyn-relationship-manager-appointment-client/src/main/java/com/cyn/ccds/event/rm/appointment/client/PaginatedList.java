package com.cyn.ccds.event.rm.appointment.client;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO for wrapping paginated results * */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedList {

  /** Total number of rows */
  long total;

  /** Found entities */
  List<?> rows;
}
