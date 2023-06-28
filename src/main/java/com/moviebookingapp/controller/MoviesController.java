package com.moviebookingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.MoviesDTO;
import com.moviebookingapp.dto.SuccessResponse;
import com.moviebookingapp.exceptions.InvalidTokenException;
import com.moviebookingapp.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.exceptions.MovieNotFoundException;
import com.moviebookingapp.exceptions.TicketNotFoundException;
import com.moviebookingapp.exceptions.UnauthorizedException;
import com.moviebookingapp.model.Movies;
import com.moviebookingapp.service.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
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

	@Operation(summary = "To add movie details ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movies added successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "404", description = "Movie already exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@PostMapping("/add")
	public ResponseEntity<?> addMovie(@RequestBody MoviesDTO movieDto,
			@RequestHeader("Authorization") SuccessResponse successResponse)
			throws InvalidTokenException, UnauthorizedException {

		if (!successResponse.getRole().stream().anyMatch(r -> r.equalsIgnoreCase("admin"))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		MessageResponse messageResponse = movieService.addMovie(movieDto);

		return ResponseEntity.ok(messageResponse);
	}

	@Operation(summary = "This API will book tickets and add it in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie Deleted Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = "Movie not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@DeleteMapping(value = "/{moviename}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteMovie(@PathVariable String moviename,
			@RequestHeader("Authorization") SuccessResponse successResponse)
			throws InvalidTokenException, UnauthorizedException, MovieNotFoundException {

		if (!userProfileController.validate(successResponse).getBody().isValid())
			throw new InvalidTokenException("Invalid token passed or token invalidated");

		if (!successResponse.getRole().stream().anyMatch(r -> r.equalsIgnoreCase("admin"))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(movieService.deleteMovie(moviename));
	}

	@Operation(summary = "This API will update the status of the tickets in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Tickets Updated Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = "Movie not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@PutMapping(value = "/{moviename}/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTicketStatus(@PathVariable("moviename") String moviename,
			@RequestHeader("Authorization") SuccessResponse successResponse)
			throws InvalidTokenException, UnauthorizedException, TicketNotFoundException {

		if (!userProfileController.validate(successResponse).getBody().isValid())
			throw new InvalidTokenException("Invalid token passed or token invalidated");

		if (!successResponse.getRole().stream().anyMatch(r -> r.equalsIgnoreCase("admin"))) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			return ResponseEntity.ok(movieService.updateTicketStatus(moviename));
		} catch (MovieNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

}
