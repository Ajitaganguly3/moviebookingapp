package com.moviebookingapp.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class LoginDetails {

	private String username;
	private String password;

	

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
