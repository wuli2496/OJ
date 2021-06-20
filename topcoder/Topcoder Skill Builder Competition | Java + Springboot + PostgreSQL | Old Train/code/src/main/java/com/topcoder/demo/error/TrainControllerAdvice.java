package com.topcoder.demo.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<GenericResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		GenericResponse genericResponse = new GenericResponse("invalid endpoint", null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(TrainNotFoundException.class)
	public ResponseEntity<GenericResponse> handleNotSearchTrain(TrainNotFoundException ex) {
		GenericResponse genericResponse = new GenericResponse(ex.getMessage(), null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
	}
	

	@ExceptionHandler(SearchParamException.class)
	public ResponseEntity<GenericResponse> handleSearchParam(SearchParamException ex) {
		GenericResponse genericResponse = new GenericResponse(ex.getMessage(), null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<GenericResponse> handleMessageNotReadable(HttpMessageNotReadableException ex) {
		GenericResponse genericResponse = new GenericResponse("failed when edit train", null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<GenericResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
		GenericResponse genericResponse = new GenericResponse("failed validation", null);
		return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.BAD_REQUEST);
	}
}
