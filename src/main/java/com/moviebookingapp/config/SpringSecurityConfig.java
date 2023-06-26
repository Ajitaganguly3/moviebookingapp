package com.moviebookingapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.moviebookingapp.convertor.HeaderConvertor;
import com.moviebookingapp.filter.JWTAuthenticationFilter;
import com.moviebookingapp.repository.UserProfileRespository;
import com.moviebookingapp.service.UserProfileService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	@Autowired
	private UserProfileService userProfileService;
	@Autowired
	private UserProfileRespository userProfileRepository;
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private HeaderConvertor headerConvertor;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/**").permitAll().anyRequest()
				.authenticated().and().exceptionHandling().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable();
		return http.build();
	}
//
//	@Bean
//	public UrlBasedCorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration configuration = new CorsConfiguration();
//		configuration.addAllowedOrigin("*");
//		configuration.addAllowedMethod("*");
//		configuration.addAllowedHeader("*");
//		configuration.setAllowCredentials(true);
//
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", configuration);
//
//		return source;
//	}

	@Bean
	public AuthenticationManager custAuthenticationManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		auth.userDetailsService(userProfileService).passwordEncoder(customPasswordEncoder());
		return auth.build();

	}

	@Bean
	public PasswordEncoder customPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public WebMvcConfigurer webMvcConfigurer() {

		return new WebMvcConfigurer() {

			@Override
			public void addFormatters(FormatterRegistry registry) {
				registry.addConverter(headerConvertor);
			}
			
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/v1.0/moviebooking/**")
				.allowedOrigins("http://127.0.0.1:5173", "http://localhost:5173")
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*");
			}
		};
	}

}
