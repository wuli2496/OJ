package com.cyn.commons.exception;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class BaseUnauthenticatedExceptionTest {
  @Test
  void testMethods() {
    SubError subError = new SubError("errorCode1");
    subError.setErrorCode("errorCode2");

    BaseUnauthenticatedException exception1 =
        new BaseUnauthenticatedException("error1", new ArrayList<>());
    BaseUnauthenticatedException exception2 = new BaseUnauthenticatedException("error2");
    BaseUnauthenticatedException exception3 =
        new BaseUnauthenticatedException("error3", "errorCode1", "errorMessage1");

    assertEquals("error1", exception1.getErrorMessage());

    assertEquals("errorMessage1", exception3.getErrorMessage());
    assertEquals("errorCode1", exception3.getErrorCode());

    assertEquals("error2", exception2.getErrorMessage());
    assertEquals(Collections.EMPTY_LIST, exception2.getSubErrors());
    assertEquals("error2", exception2.getErrorCode());
  }
}
