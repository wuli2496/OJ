package com.topcoder.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.topcoder.demo.dto.GenericResponse;

@ControllerAdvice
public class TrainControllerAdvice{

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<GenericResponse> handleNoHanderMatch(NoHandlerFoundException ex) {
		GenericResponse genericResponse = new GenericResponse("invalid endpoint", null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<GenericResponse> handleEntityNotFound(EntityNotFoundException ex) {
		GenericResponse genericResponse = new GenericResponse(ex.getMessage(), null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
	}
}
