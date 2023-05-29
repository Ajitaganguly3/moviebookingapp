package com.moviebookingapp.dto;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class TicketDTO {

	@NotBlank
	private String ticketId;
	@NotEmpty(message = "Movie name cannot be empty or null")
	private String moviename;
	@NotEmpty(message = "Theatre name cannot be empty or null")
	private String theatrename;
	@NotBlank
	@Min(value = 0)
	private int noOfTickets;
	@NotEmpty(message = "Seat number cannot be empty or null")
	private List<String> seatnumber;

}
