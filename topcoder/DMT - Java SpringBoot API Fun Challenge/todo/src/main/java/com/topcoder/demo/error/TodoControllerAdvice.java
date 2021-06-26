package com.topcoder.demo.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.topcoder.demo.dto.GenericResponse;

@RestControllerAdvice
public class TodoControllerAdvice {
	@ExceptionHandler(InvalidTokenException.class)
	public GenericResponse handleNoHanderMatch(InvalidTokenException ex) {
		return GenericResponse.failure(-1, "invalid token");
	}
}
