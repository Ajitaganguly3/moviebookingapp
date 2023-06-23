package com.moviebookingapp.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {

	private String token;
	private HttpStatus statusCode;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	

}
