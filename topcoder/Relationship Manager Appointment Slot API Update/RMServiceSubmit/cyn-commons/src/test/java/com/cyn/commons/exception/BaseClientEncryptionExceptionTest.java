package com.cyn.commons.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BaseClientEncryptionExceptionTest {

  @Test
  void init() {
    List<SubError> subErrors =
        new ArrayList<>() {
          {
            add(new SubError("error1"));
          }
        };
    BaseClientEncryptionException exception = new BaseClientEncryptionException("error", subErrors);
    assertNotNull(exception);
    assertEquals("error", exception.getErrorMessage());
    assertEquals(subErrors, exception.getSubErrors());
  }
}
