package com.moviebookingapp.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LoginDetails {

	private String loginId;
	 private String password;
	
}


