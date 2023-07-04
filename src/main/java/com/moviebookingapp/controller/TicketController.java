package com.moviebookingapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.SuccessResponse;
import com.moviebookingapp.dto.TicketDTO;
import com.moviebookingapp.exceptions.InvalidTokenException;
import com.moviebookingapp.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.exceptions.MovieNotFoundException;
import com.moviebookingapp.exceptions.TicketAlreadyExistException;
import com.moviebookingapp.exceptions.TicketNotFoundException;
import com.moviebookingapp.exceptions.UnauthorizedException;
import com.moviebookingapp.model.Ticket;
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
@CrossOrigin(origins = { "http://localhost:5173", "http://127.0.0.1:5173" })
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

//	@PostMapping(value = "/{moviename}/book", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<MessageResponse> bookTicket(@PathVariable("moviename") String moviename,
//			@RequestBody TicketDTO ticketDTO, @RequestHeader("Authorization") SuccessResponse successResponse)
//			throws InvalidTokenException, UnauthorizedException, TicketAlreadyExistException {
//		if (!userProfileController.validate(successResponse).getBody().isValid())
//			throw new InvalidTokenException("Invalid token passed or token invalidated");
//		log.info(ticketDTO.toString());
//
//		return ResponseEntity.status(HttpStatus.CREATED).body(ticketService.bookTicket(ticketDTO));
//
//	}

	@PostMapping(value = "/{moviename}/book", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookTicket(@PathVariable("moviename") String moviename, HttpServletRequest request,
			@RequestHeader("Authorization") SuccessResponse successResponse)
			throws MovieAlreadyExistsException, InvalidTokenException, UnauthorizedException, MovieNotFoundException {

		if (!userProfileController.validate(successResponse).getBody().isValid())
			throw new InvalidTokenException("Invalid token passed or token invalidated");
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			TicketDTO ticketDTO = objectMapper.readValue(request.getInputStream(), TicketDTO.class);
			log.info(ticketDTO.toString());
			return ResponseEntity.ok(ticketService.bookTicket(moviename, ticketDTO));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}

	}

	

}
