package com.moviebookingapp.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.moviebookingapp.model.Movies;

import lombok.Data;

@Component

public class TicketDTO implements Serializable {

	private static final long serialVersionUID = -1151637347452772181L;
	private String _id;
	@NotEmpty(message = "Movie name cannot be empty or null")
	private String moviename;
	@NotEmpty(message = "Theatre name cannot be empty or null")
	private String theatrename;
	@NotEmpty(message = "No. of tickets cannot be empty or null")
	private int noOfTickets;
	@NotEmpty(message = "Seat number cannot be empty or null")
	private List<String> seatnumber;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
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
