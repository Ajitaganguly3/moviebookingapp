package com.moviebookingapp.exceptions;

public class MovieAlreadyExistsException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2781527715372642086L;
	
	public MovieAlreadyExistsException(String message) {
		super(message);
	}

}
