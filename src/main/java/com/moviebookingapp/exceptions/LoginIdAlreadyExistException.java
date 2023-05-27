package com.moviebookingapp.exceptions;

public class LoginIdAlreadyExistException extends Exception {

	private static final long serialVersionUID = 1L;

	public LoginIdAlreadyExistException(String message) {
		super(message);
	}

}
