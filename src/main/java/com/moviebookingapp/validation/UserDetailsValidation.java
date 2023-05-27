package com.moviebookingapp.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailsValidation {
	
	public static boolean isPasswordValid(String password) {
		log.info("Password validation started");
		String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		if(password==null) return false;
		else {
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(password);
			return m.matches();
		}
	}	
	public static boolean compareConfirmPasswordAndPasswordFields(String password,String confirmPassword) {
		return password.equals(confirmPassword);
	}


}
