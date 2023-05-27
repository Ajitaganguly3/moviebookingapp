package com.moviebookingapp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.SuccessResponse;
import com.moviebookingapp.dto.TicketDTO;
import com.moviebookingapp.exceptions.MovieNotFoundException;
import com.moviebookingapp.exceptions.TicketNotFoundException;
import com.moviebookingapp.model.Movies;
import com.moviebookingapp.model.Ticket;
import com.moviebookingapp.repository.MoviesRepository;
import com.moviebookingapp.repository.TicketRepository;

@Service
public class TicketService {

	private TicketRepository ticketRepository;
	private MoviesRepository movieRepository;

	public TicketService(TicketRepository ticketRepository, MoviesRepository moviesRepository) {
		this.ticketRepository = ticketRepository;
		this.movieRepository = moviesRepository;
	}

	public MessageResponse bookTicket(String moviename, @Valid TicketDTO ticketDTO) throws MovieNotFoundException {
		
		Optional<Movies> movie = movieRepository.findByMovienameOrderByMovienameAsc(moviename);
		if (movie.isEmpty()) {
			throw new MovieNotFoundException("Movie doesn't exist with the name: " + moviename);
		}
		
		if(!moviename.equals(ticketDTO.getMoviename())) {
			throw new MovieNotFoundException("Movie name doesn't match");
		}
		
		Ticket ticket = convertToDTO(ticketDTO);
		ticketRepository.save(ticket);
		
		//MessageResponse messageResponse = new MessageResponse("Ticket Booked Successfully", null);
		//messageResponse.setMessage("Ticket Booked Successfully");
//		ObjectMapper objectMapper = new ObjectMapper();
//		String response = null;
//		try {
//			response = objectMapper.writeValueAsString(messageResponse);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return new MessageResponse("Ticket Booked Successfully", HttpStatus.OK);

	}

	public MessageResponse updateTicketStatus(String moviename) throws TicketNotFoundException, MovieNotFoundException {
		Optional<Movies> movie = movieRepository.findByMovienameOrderByMovienameAsc(moviename);
		if (movie.isPresent()) {
			Movies existingMovie = movie.get();
			int totalBookedTickets = getBookedTicketCount(moviename);

			if (totalBookedTickets == existingMovie.getNoOfTicketsAllotted()) {
				existingMovie.setStatus("SOLD OUT");
			} else {
				existingMovie.setStatus("BOOK ASAP");
			}
			movieRepository.save(existingMovie);
		} else {
			throw new MovieNotFoundException("Movie not found with the name: " + moviename);
		}

		return new MessageResponse("Tickets Updated Successfully", HttpStatus.OK);
	}

	public MessageResponse deleteMovie(String moviename) throws MovieNotFoundException {

		Optional<Movies> movie = movieRepository.findByMovienameOrderByMovienameAsc(moviename);
		if (movie.isPresent()) {
			movieRepository.delete(movie.get());
		} else {
			throw new MovieNotFoundException("Movie not found with the name: " + moviename);
		}
		return new MessageResponse("Movie Deleted Successfully", HttpStatus.OK);

	}

	private int getBookedTicketCount(String moviename) {
		List<Ticket> tickets = ticketRepository.findByMovieName(moviename);
		return tickets.stream().mapToInt(Ticket::getNoOfTickets).sum();
	}

	private Ticket convertToDTO(TicketDTO ticketDTO) {
		Ticket ticket = new Ticket();
		ticket.setMoviename(ticketDTO.getMoviename());
		ticket.setTheatrename(ticketDTO.getTheatrename());
		ticket.setNoOfTickets(ticketDTO.getNoOfTickets());
		ticket.setSeatnumber(ticketDTO.getSeatnumber());

		return ticket;
	}

}
