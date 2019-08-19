package com.dan.imdbapi.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Pojo class to encapsulate data from exception
 * 
 * @author daniel
 *
 */
public class ExceptionResponse {

	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "America/Recife")
	private Date timestamp;
	private String message;
	private int status;
	private String path;

	public ExceptionResponse(Exception ex, HttpStatus status, String path) {
		this.timestamp = new Date();
		this.message = ex.getMessage();
		this.status = status.value();
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
