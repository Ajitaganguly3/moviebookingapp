package com.moviebookingapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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
import com.moviebookingapp.service.UserProfileService;
import com.moviebookingapp.util.JWTUtil;
import com.moviebookingapp.convertor.HeaderConvertor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
@Slf4j
public class UserProfileController {

	UserProfileService userProfileService;
	JWTUtil jwtUtil;
	AuthenticationManager authenticationManager;

	@Autowired
	public UserProfileController(UserProfileService userProfileService, JWTUtil jwtUtil,
			AuthenticationManager authenticationManager) {
		// TODO Auto-generated constructor stub
		this.userProfileService = userProfileService;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Operation(summary = "To perform login operation and generate JWT Token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login Successful and JWT Token generated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "401", description = "Wrong credentials", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))) })
	@PostMapping("/login")
	public ResponseEntity<SuccessResponse> login(@RequestBody LoginDetails loginDetails) throws LoginException {
		// String methodName = "login()";
		// log.info("{} is called ", methodName);
		UserDetails userDetails;
		String methodName = "login()";
		log.info("{} is called ", methodName);

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDetails.getUsername(), loginDetails.getPassword()));
		} catch (UsernameNotFoundException exception) {
			throw new LoginException("Enter correct username or password");
		}

		userDetails = userProfileService.loadUserByUsername(loginDetails.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		List<String> role = userDetails.getAuthorities().stream().map(m -> m.getAuthority())
				.collect(Collectors.toList());
//		SuccessResponse successResponse = userProfileService.login(loginDetails);
		return ResponseEntity.ok(new SuccessResponse(token, role, userDetails.getUsername()));
	}

	/**
	 * @param userDetails It contains the complete user details which is necessary
	 *                    for user registration
	 * @return {@link ResponseEntity} of type {@link MessageResponse} when user
	 *         register successfully
	 * @throws LoginIdAlreadyExistException when user with same loginId already
	 *                                      registered
	 * @throws InvalidPasswordException     when password validation failed that is
	 *                                      password must have atleast one
	 *                                      uppercase, one lowercase, one special
	 *                                      character(@,_,-,& etc) and one number.
	 *                                      It should have minimum length of 8
	 */
	@Operation(summary = "To perform registration")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "username already exist or password validation failed", content = @Content) })
	@PostMapping("/register")
	public ResponseEntity<MessageResponse> register(@RequestBody UserProfileDTO userDetails)
			throws InvalidPasswordException, LoginIdAlreadyExistException {
		// String methodName = "register()";
		// log.info("{} is called ", methodName);
		MessageResponse messageResponse = userProfileService.register(userDetails);
		return ResponseEntity.ok(messageResponse);
	}

	@Operation(summary = "To perform user's token validation")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User's token validated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "401", description = "Invalid Token", content = @Content) })

	@GetMapping("/validate")
	public ResponseEntity<AuthResponse> validate(@RequestBody SuccessResponse successResponse)
			throws InvalidTokenException, UnauthorizedException {
		// String methodName = "validate()";
		// log.info("{} invoked: validation started", methodName);
		return ResponseEntity.ok(userProfileService.validate(successResponse));
	}

	@GetMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@Operation(summary = "Forgot Password")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Password Updated Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "400", description = "Login Id does not exist", content = @Content) })

	@GetMapping("/{userName}/forgot")
	public ResponseEntity<MessageResponse> forgotPassword(@PathVariable String userName,
			@RequestHeader("Authorization") SuccessResponse successResponse,
			@RequestHeader String password) throws InvalidTokenException, UnauthorizedException {
		if (!userProfileService.validate(successResponse).isValid())
			throw new InvalidTokenException("Invalid token passed");
		return ResponseEntity.ok(userProfileService.forgotPassword(userName, successResponse.getToken(), password));

	}

}
