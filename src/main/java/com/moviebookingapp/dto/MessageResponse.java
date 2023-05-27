package com.moviebookingapp.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageResponse {

	private String message;
	private HttpStatus statusCode;

}
