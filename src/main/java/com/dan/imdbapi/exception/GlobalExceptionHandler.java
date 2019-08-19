package com.dan.imdbapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Class to handle exceptions globally
 * @author daniel
 *
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { ObjectNotFoundException.class })
	public ResponseEntity<ExceptionResponse> handleObjecNotFound(ObjectNotFoundException ex, WebRequest request) {
		String requestURI = ((ServletWebRequest)request).getRequest().getRequestURI();
		ExceptionResponse response = new ExceptionResponse(ex, HttpStatus.NOT_FOUND, requestURI);
		log.error(String.format("Object not found when accessing path %s", requestURI));
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

}
