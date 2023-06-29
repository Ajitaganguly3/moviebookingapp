package com.moviebookingapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
import com.moviebookingapp.model.Role;
import com.moviebookingapp.model.UserProfile;
import com.moviebookingapp.repository.UserProfileRespository;
import com.moviebookingapp.util.JWTUtil;
import com.moviebookingapp.validation.UserDetailsValidation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserProfileServiceImpl implements UserProfileService {

	@Autowired
	private UserProfileRespository userProfileRepository;

	@Autowired
	private JWTUtil jwtUtil;

//	@Autowired
//	public UserProfileServiceImpl(UserProfileRespository userProfileRespository, JWTUtil jwtUtil) {
//		// TODO Auto-generated constructor stub
//		this.userProfileRepository = userProfileRespository;
//		this.jwtUtil = jwtUtil;
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// String methodName = "loadUserByUsername()";
		// log.info("{} invoked", methodName);
		UserProfile user = userProfileRepository.findById(username).get();
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

		user.getRoles().forEach(role -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
		});
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

//	@Override
//	public SuccessResponse login(LoginDetails loginDetails) throws LoginException {
//		// String methodName = "login()";
//		// log.info("{} method invoked. In Process", methodName);
//		UserDetails userDetails = loadUserByUsername(loginDetails.getUsername());
//		if (userDetails.getPassword().equalsIgnoreCase(loginDetails.getPassword())) {
//			// log.info("{} ran successfully. Token will be generated", methodName);
//
//			return new SuccessResponse(jwtUtil.generateToken(userDetails), HttpStatus.OK);
//		}
//		// log.info("Login failure. {} thrown exception", methodName);
//		throw new LoginException("Login Failed Please enter correct credentials");
//	}

	@Override
	public MessageResponse register(@Valid UserProfileDTO userDetails)
			throws InvalidPasswordException, LoginIdAlreadyExistException {

		Optional<UserProfile> existingUser = userProfileRepository.findById(userDetails.getUsername());
		if (existingUser.isPresent()) {
			throw new LoginIdAlreadyExistException("username already taken");
		}
		// String methodName = "register()";
		// log.info("{} invoked: ", methodName);
		if (!UserDetailsValidation.isPasswordValid(userDetails.getPassword())) {
			// log.info("Inside {} . Password validation failed ");
			throw new InvalidPasswordException(
					"Password should have atleast 1 lowercase, uppercase, special character");
		}

		if (!UserDetailsValidation.compareConfirmPasswordAndPasswordFields(userDetails.getPassword(),
				userDetails.getConfirmPassword())) {
			// log.info("Inside {} . 'confirmPassword' and 'Password' validation failed ",
			// methodName);
			throw new InvalidPasswordException("Please enter the same password in both fields");
		}

		UserProfile userProfile = new UserProfile();
		userProfile.setFirstName(userDetails.getFirstName());
		userProfile.setLastName(userDetails.getLastName());
		userProfile.setEmail(userDetails.getEmail());
		userProfile.setUsername(userDetails.getUsername());
		userProfile.setPassword(userDetails.getPassword());
		userProfile.setConfirmPassword(userDetails.getConfirmPassword());
		userProfile.setContactNumber(userDetails.getContactNumber());
		userProfile.setRoles(userDetails.getRoles());
		userProfileRepository.save(userProfile);
		return new MessageResponse("User Registered Successfully", HttpStatus.OK);
	}

	@Override
	public AuthResponse validate(SuccessResponse successResponse) throws InvalidTokenException, UnauthorizedException {
		Set<Role> roles = userProfileRepository.findById(successResponse.getUsername()).get().getRoles();

		if (!successResponse.getRole().stream()
				.anyMatch(r -> roles.stream().anyMatch(t -> r.equalsIgnoreCase(t.getAuthority())))) {
			throw new InvalidTokenException("user is not authorized to access");
		}
		if (!jwtUtil.validateToken(successResponse.getToken()))
			throw new InvalidTokenException("Invalid Token");
		return new AuthResponse(jwtUtil.extractUsername(successResponse.getToken()),
				jwtUtil.validateToken(successResponse.getToken()));

	}

	@Override
	public MessageResponse forgotPassword(String customerName, String token, String password) {

//		UserProfile userEntity = userProfileRepository.findByFirstName(customerName).get().stream()
//				.filter(f -> f.getUsername().equals(jwtUtil.extractUsername(token))).findFirst().get();
//		userEntity.setPassword(password);
//		userProfileRepository.save(userEntity);
//		return new MessageResponse("password updated successfully", HttpStatus.OK);

		Optional<List<UserProfile>> userProfileOptional = userProfileRepository.findByFirstName(customerName);
		if (userProfileOptional.isPresent() && !userProfileOptional.get().isEmpty()) {
			List<UserProfile> userProfile = userProfileOptional.get();
			UserProfile userEntity = userProfile.stream()
					.filter(f -> f.getUsername().equals(jwtUtil.extractUsername(token))).findFirst()
					.orElseThrow(() -> new NoSuchElementException("User not found"));
			userEntity.setPassword(password);
			userProfileRepository.save(userEntity);
			return new MessageResponse("Password updated successfully", HttpStatus.OK);
		} else {
			throw new NoSuchElementException("User not found!!");
		}

	}

}
