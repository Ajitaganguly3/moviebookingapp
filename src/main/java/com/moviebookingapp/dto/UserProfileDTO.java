package com.moviebookingapp.dto;

import java.io.Serializable;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.moviebookingapp.model.Role;

import lombok.Data;

@Component
@Data
public class UserProfileDTO implements Serializable{
	
	private static final long serialVersionUID = -1151637347452772181L;
	@NotEmpty(message = "First Name cannot be empty or null")
	private String firstName;
	@NotEmpty(message = "Last Name cannot be empty or null")
	private String lastName;
	@Email(message = "Email should be valid")
	private String email;
	@NotEmpty
	private String username;
	@NotEmpty
	private String contactNumber;
	@Min(value = 8,message = "Password should be of length 8 atleast")
	private String password;
	@Min(value = 8,message = "Password should be of length 8 atleast")
	private String confirmPassword;
	private Set<Role> roles;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
