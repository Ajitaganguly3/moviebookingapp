package com.moviebookingapp.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.exceptions.InvalidPasswordException;
import com.moviebookingapp.exceptions.LoginException;
import com.moviebookingapp.exceptions.LoginIdAlreadyExistException;
import com.moviebookingapp.exceptions.UnauthorizedException;

@ControllerAdvice
public class LoginAndRegisterExceptionHandler {
	
	@ExceptionHandler({LoginException.class,UnauthorizedException.class})
	public ResponseEntity<MessageResponse> loginExceptionHandler(Exception ex) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new MessageResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED));
	}

	@ExceptionHandler({InvalidPasswordException.class,LoginIdAlreadyExistException.class})
	public ResponseEntity<MessageResponse> invalidInputExceptionHandler(Exception ex) {
		return new ResponseEntity<MessageResponse>(new MessageResponse(ex.getMessage(), HttpStatus.BAD_REQUEST),
				HttpStatus.BAD_REQUEST);
	}


}
