package com.moviebookingapp.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@Component
public class TicketDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2786752437721636754L;

	@NotEmpty
	@JsonProperty("_id")
	private String _id;

	@NotEmpty(message = "Movie name cannot be empty or null")
	@JsonProperty("moviename")
	private String moviename;

	@NotEmpty(message = "Theatre name cannot be empty or null")
	@JsonProperty("theatrename")
	private String theatrename;

	@NotNull(message = "No. of tickets cannot be null")
	@JsonProperty("noOfTickets")
	@Min(value = 0, message = "*No. of tickets cannot be negative")
	private int noOfTickets;

	@NotEmpty(message = "Seat number cannot be empty or null")
	@JsonProperty("seatnumber")
	private List<String> seatnumber;

	

	public String getMoviename() {
		return moviename;
	}

	public void setMoviename(String moviename) {
		this.moviename = moviename;
	}

	public String getTheatrename() {
		return theatrename;
	}

	public void setTheatrename(String theatrename) {
		this.theatrename = theatrename;
	}

	public int getNoOfTickets() {
		return noOfTickets;
	}

	public void setNoOfTickets(int noOfTickets) {
		this.noOfTickets = noOfTickets;
	}

	public List<String> getSeatnumber() {
		return seatnumber;
	}

	public void setSeatnumber(List<String> seatnumber) {
		this.seatnumber = seatnumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
