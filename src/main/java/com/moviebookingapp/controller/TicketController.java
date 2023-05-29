package com.moviebookingapp.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.SuccessResponse;
import com.moviebookingapp.dto.TicketDTO;
import com.moviebookingapp.exceptions.InvalidTokenException;
import com.moviebookingapp.exceptions.MovieNotFoundException;
import com.moviebookingapp.exceptions.TicketAlreadyExistException;
import com.moviebookingapp.exceptions.TicketNotFoundException;
import com.moviebookingapp.exceptions.UnauthorizedException;
import com.moviebookingapp.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TicketController {

	private TicketService ticketService;
	private UserProfileController userProfileController;

	public TicketController(TicketService ticketService, UserProfileController userProfileController) {
		this.ticketService = ticketService;
		this.userProfileController = userProfileController;
	}

	@Operation(summary = "This API will book tickets and add it in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Booked Ticket Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = "Ticket already Exists", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@PostMapping(value = "/{moviename}/add", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MessageResponse> bookTicket(@PathVariable("moviename") String moviename,
			@Valid @RequestBody TicketDTO ticketDTO, @RequestHeader("Authorization") String token,
			@RequestHeader("Role") String role)
			throws InvalidTokenException, UnauthorizedException, TicketAlreadyExistException {
		if (!userProfileController.validate(token, role).getBody().isValid())
			throw new InvalidTokenException("Invalid token passed or token invalidated");
		log.info(ticketDTO.toString());

		return ResponseEntity.ok(ticketService.bookTicket(ticketDTO));

	}

	@Operation(summary = "This API will update the status of the tickets in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Tickets Updated Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = "Movie not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@PutMapping(value = "/{moviename}/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTicketStatus(@PathVariable("moviename") String moviename,
			@RequestHeader("Authorization") String token, @RequestHeader("Role") String role)
			throws InvalidTokenException, UnauthorizedException, TicketNotFoundException {

		if (!userProfileController.validate(token, role).getBody().isValid())
			throw new InvalidTokenException("Invalid token passed or token invalidated");

		if (!role.equals("Admin")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {
			return ResponseEntity.ok(ticketService.updateTicketStatus(moviename));
		} catch (MovieNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@Operation(summary = "This API will book tickets and add it in the database")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Movie Deleted Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class))),
			@ApiResponse(responseCode = "400", description = "Movie not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
			@ApiResponse(responseCode = "403", description = "Invalid token passed or token invalidated", content = @Content) })

	@DeleteMapping(value = "/{moviename}/delete", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteMovie(@PathVariable String moviename, @RequestHeader("Authorization") String token,
			@RequestHeader("Role") String role)
			throws InvalidTokenException, UnauthorizedException, MovieNotFoundException {

		if (!userProfileController.validate(token, role).getBody().isValid())
			throw new InvalidTokenException("Invalid token passed or token invalidated");

		if (!role.equals("Admin")) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(ticketService.deleteMovie(moviename));
	}

}
