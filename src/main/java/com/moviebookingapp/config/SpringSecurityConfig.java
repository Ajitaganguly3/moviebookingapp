package com.moviebookingapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.moviebookingapp.filter.JWTAuthenticationFilter;
import com.moviebookingapp.repository.UserProfileRespository;
import com.moviebookingapp.service.UserProfileService;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  {
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private UserProfileRespository userProfileRepository;
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		return http.build();
	}

}
