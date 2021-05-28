package com.password.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleConstraintViolationException(
			ConstraintViolationException ex, WebRequest request) {
		
		ErrorResponse error = new ErrorResponse("Validation Error", ex.getLocalizedMessage());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse("Internal Server Error", ex.getLocalizedMessage());
		return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<Object> handleAllException(TransactionSystemException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse("Validation Error", ex.getCause().getCause().getMessage());
		return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
	}

}
