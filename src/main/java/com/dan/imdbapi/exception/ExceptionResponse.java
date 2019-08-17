package com.dan.imdbapi.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {

	@JsonFormat(pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "America/Recife")
	private Date timestamp;
	private String message;
	private int status;

	public ExceptionResponse(Exception ex, HttpStatus status) {
		this.timestamp = new Date();
		this.message = ex.getMessage();
		this.status = status.value();
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
