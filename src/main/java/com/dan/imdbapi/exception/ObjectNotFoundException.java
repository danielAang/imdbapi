package com.dan.imdbapi.exception;

public class ObjectNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException() {
	}

	public ObjectNotFoundException(String message) {
		super(message);
	}
}
