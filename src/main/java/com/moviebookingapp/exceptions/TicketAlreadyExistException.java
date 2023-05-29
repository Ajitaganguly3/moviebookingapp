package com.moviebookingapp.exceptions;

public class TicketAlreadyExistException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3956851461695561007L;

	public TicketAlreadyExistException(String message) {
		super(message);
	}

}
