package com.cyn.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class BaseServiceExceptionTest {
  /*
   * Tests various constructors/methods of base service exception which is extended by other
   * exceptions.*/

  @Test
  void testMethods() {
    SubError subError = new SubError("errorCode1");
    subError.setErrorCode("errorCode2");

    BaseServiceException exception1 = new BaseServiceException("error1", new ArrayList<>());
    BaseServiceException exception2 =
        new BaseServiceException("error2", new ArrayList<>(), new RuntimeException());
    BaseServiceException exception3 =
        new BaseServiceException(
            "error3",
            new RuntimeException(),
            "errorCode1",
            "errorMessage1",
            HttpStatus.INTERNAL_SERVER_ERROR,
            new ArrayList<>() {
              {
                add(subError);
              }
            });

    BaseServiceException exception4 =
        new BaseServiceException("message", "errorCode", "errorMessage");
    BaseServiceException exception5 = new BaseServiceException("errorCode");
    BaseServiceException exception6 =
        new BaseServiceException("errorCode", HttpStatus.INTERNAL_SERVER_ERROR);

    assertEquals("error1", exception1.getErrorMessage());

    assertEquals("errorMessage1", exception3.getErrorMessage());
    assertEquals(1, exception3.getSubErrors().size());
    assertEquals(subError.getErrorCode(), exception3.getSubErrors().get(0).getErrorCode());
    assertEquals("errorCode1", exception3.getErrorCode());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception3.getHttpStatus());

    assertEquals("error2", exception2.getErrorMessage());
    assertEquals(Collections.EMPTY_LIST, exception2.getSubErrors());
    assertEquals("error2", exception2.getErrorCode());

    assertEquals("errorCode", exception4.getErrorCode());
    assertEquals("message", exception4.getMessage());
    assertEquals("errorMessage", exception4.getErrorMessage());

    assertEquals("errorCode", exception5.getErrorCode());
    assertEquals("errorCode", exception5.getErrorMessage());
    assertEquals(new ArrayList<>(), exception5.getSubErrors());

    assertEquals("errorCode", exception6.getErrorCode());
    assertEquals("errorCode", exception6.getErrorMessage());
    assertEquals(new ArrayList<>(), exception6.getSubErrors());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception6.getHttpStatus());
  }
}
