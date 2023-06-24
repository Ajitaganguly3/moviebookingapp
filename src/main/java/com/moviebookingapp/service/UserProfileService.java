package com.moviebookingapp.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.moviebookingapp.dto.AuthResponse;
import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.SuccessResponse;
import com.moviebookingapp.dto.UserProfileDTO;
import com.moviebookingapp.exceptions.InvalidPasswordException;
import com.moviebookingapp.exceptions.InvalidTokenException;
import com.moviebookingapp.exceptions.LoginException;
import com.moviebookingapp.exceptions.LoginIdAlreadyExistException;
import com.moviebookingapp.exceptions.UnauthorizedException;
import com.moviebookingapp.model.LoginDetails;

public interface UserProfileService extends UserDetailsService {

//	public SuccessResponse login(LoginDetails loginDetails) throws LoginException;

	public MessageResponse register(UserProfileDTO userDetails)
			throws InvalidPasswordException, LoginIdAlreadyExistException;

	public AuthResponse validate(SuccessResponse successResponse) throws InvalidTokenException, UnauthorizedException;

	public MessageResponse forgotPassword(String customerName, String token, String password);

}
