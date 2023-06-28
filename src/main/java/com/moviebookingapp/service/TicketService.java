package com.moviebookingapp.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.moviebookingapp.dto.MessageResponse;
import com.moviebookingapp.dto.TicketDTO;
import com.moviebookingapp.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.exceptions.MovieNotFoundException;
import com.moviebookingapp.exceptions.TicketAlreadyExistException;
import com.moviebookingapp.exceptions.TicketNotFoundException;
import com.moviebookingapp.model.Movies;
import com.moviebookingapp.model.Ticket;
import com.moviebookingapp.repository.MoviesRepository;
import com.moviebookingapp.repository.TicketRepository;

@Service
public class TicketService {

	private TicketRepository ticketRepository;
	private MoviesRepository movieRepository;

	@Autowired
	public TicketService(TicketRepository ticketRepository, MoviesRepository moviesRepository) {
		this.ticketRepository = ticketRepository;
		this.movieRepository = moviesRepository;
	}

//	public MessageResponse bookTicket(@Valid TicketDTO ticketDto) throws TicketAlreadyExistException {
//
//		Optional<Movies> movie = movieRepository.findByMoviename(ticketDto.getMoviename());
//		if (!movie.isPresent()) {
//			throw new TicketAlreadyExistException("Movie doesn't exist with the name: " + ticketDto.getMoviename());
//		}
//
//		Ticket ticket = new Ticket();
//		ticket.setMoviename(ticketDto.getMoviename());
//		ticket.setTheatrename(ticketDto.getTheatrename());
//		ticket.setNoOfTickets(ticketDto.getNoOfTickets());
//		ticket.setSeatnumber(ticketDto.getSeatnumber());
//		ticket.setTicketId(ticketDto.getTicketId());
//
//		ticketRepository.save(ticket);
//
//		return new MessageResponse("Ticket booked successfully", HttpStatus.OK);
//
//	}

	public MessageResponse bookTicket(String moviename, TicketDTO ticketDTO)
			throws MovieAlreadyExistsException, MovieNotFoundException {
		Optional<Movies> optionalMovie = movieRepository.findByMoviename(moviename);
		if (optionalMovie.isPresent()) {
			Movies movie = optionalMovie.get();

			if (movie.getNoOfTicketsAllotted() < ticketDTO.getNoOfTickets()) {
				throw new RuntimeException("Not enough tickets available");
			}

			Ticket ticket = new Ticket();
			ticket.setMoviename(moviename);
			ticket.setTheatrename(movie.getTheatrename());
			ticket.setNoOfTickets(ticketDTO.getNoOfTickets());
			ticket.setSeatnumber(ticketDTO.getSeatnumber());

			movie.setNoOfTicketsAllotted(movie.getNoOfTicketsAllotted() - ticketDTO.getNoOfTickets());
			movieRepository.save(movie);

			ticket = ticketRepository.save(ticket);
			return new MessageResponse("Tickets Booked Successfully", HttpStatus.OK);
		} else {
			throw new MovieNotFoundException("Movie not found with the name: " + moviename);
		}

	}

//	private int getBookedTicketCount(String moviename) {
//		List<Ticket> tickets = ticketRepository.findByMovieName(moviename);
//		return tickets.stream().mapToInt(Ticket::getNoOfTickets).sum();
//	}

//	private TicketDTO convertToDTO(Ticket ticket) {
//		TicketDTO ticketDTO = new TicketDTO();
//		ticketDTO.set_id(ticket.get_id());
//		ticketDTO.setMoviename(ticket.getMoviename());
//		ticketDTO.setTheatrename(ticket.getTheatrename());
//		ticketDTO.setNoOfTickets(ticket.getNoOfTickets());
//		ticketDTO.setSeatnumber(ticket.getSeatnumber());
//
//		return ticketDTO;
//	}

}
