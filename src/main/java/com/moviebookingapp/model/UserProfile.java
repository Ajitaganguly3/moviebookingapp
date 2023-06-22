package com.moviebookingapp.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document
public class UserProfile {
	
	@Id
	private String username;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private String contactNumber;
	private String password;
	private String confirmPassword;
	private Set<Role> roles;


}
