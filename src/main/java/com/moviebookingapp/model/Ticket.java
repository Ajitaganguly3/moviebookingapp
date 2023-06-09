package com.moviebookingapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Data
@Document
public class Ticket {

	@Id
	private String _id;

	private String moviename;
	private String theatrename;
	private int noOfTickets;
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

}
