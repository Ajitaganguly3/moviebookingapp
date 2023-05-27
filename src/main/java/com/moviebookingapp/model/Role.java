package com.moviebookingapp.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
public class Role implements GrantedAuthority{
	
	private static final long serialVersionUID = 1L;
	private String role;
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}

	public Role() {
		
	}
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
