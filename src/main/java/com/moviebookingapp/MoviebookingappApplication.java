package com.moviebookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@SpringBootApplication
@EnableWebMvc
@EnableMongoRepositories
public class MoviebookingappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviebookingappApplication.class, args);
	}
	
	@Bean
	public OpenAPI applyLoanOpenAPI() {
		String description = "This application performs Movie Booking services for both user and admin";
		String email = "ajita.ganguly@cognizant.com";
		return new OpenAPI().info(new Info().title("Movie Booking App").description(description)
				.contact(new Contact().email(email).name("Ajita Ganguly").url("ajita.com")));
	}

}