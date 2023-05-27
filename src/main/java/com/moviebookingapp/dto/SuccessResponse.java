package com.moviebookingapp.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SuccessResponse {

	private String token;
	private HttpStatus statusCode;


}
