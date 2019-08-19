package com.dan.imdbapi.exception;

/**
 * Custom exception used on services
 * @author daniel
 *
 */
public class ObjectNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException() {
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
