package com.moviebookingapp.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {

	private String message;
	private HttpStatus statusCode;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public MessageResponse(String message, HttpStatus statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}

	public MessageResponse() {
		super();
	}
	
	

}
