package com.moviebookingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.MoviesDTO;
import com.moviebookingapp.dto.SuccessResponse;
import com.moviebookingapp.exceptions.InvalidTokenException;
import com.moviebookingapp.exceptions.UnauthorizedException;
import com.moviebookingapp.service.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class MoviesController {

	private MovieService movieService;
	private UserProfileController userProfileController;

	@Autowired
	public MoviesController(MovieService movieService, UserProfileController userProfileController) {
		this.movieService = movieService;
		this.userProfileController = userProfileController;
	}

	@Operation(summary = "To perform search all movies")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movies viewed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@GetMapping("/all")
	public ResponseEntity<List<MoviesDTO>> getAllMovies(@RequestHeader("Authorization") SuccessResponse successResponse)
			throws InvalidTokenException, UnauthorizedException {
		if (!userProfileController.validate(successResponse).getBody().isValid()) {
			throw new InvalidTokenException("Invalid token passed or token invalidated");
		}
		List<MoviesDTO> movies = movieService.getAllMovies();
		return ResponseEntity.ok(movies);
	}

	@Operation(summary = "To perform search movies via movie name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movies with the movie name viewed successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "404", description = "Not found any movies with the movie name", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@GetMapping("/movies/search/{moviename}")
	public ResponseEntity<?> searchMovies(@PathVariable String moviename,
			@RequestHeader("Authorization") SuccessResponse successResponse)
			throws InvalidTokenException, UnauthorizedException {

		if (!userProfileController.validate(successResponse).getBody().isValid()) {
			throw new InvalidTokenException("Invalid token passed or token invalidated");
		}
		List<MoviesDTO> movies = movieService.searchMovies(moviename);

		if (movies.isEmpty()) {
			String message = "No movie found with the name/alphabet:" + moviename;
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}

		return ResponseEntity.ok(movies);
	}

//	@PostMapping("/add")
//	public Movies addMovie(@RequestBody MoviesDTO movieDto) {
//		return movieService.addMovie(movieDto);
//	}

}
