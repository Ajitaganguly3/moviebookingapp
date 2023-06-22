package com.moviebookingapp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MoviesDTO implements Serializable{
	
	private static final long serialVersionUID = -1151637347452772181L;
	
	@NotEmpty(message = "Movie Name cannot be empty or null")
	private String moviename;
	@NotEmpty(message = "Theatre Name cannot be empty or null")
	private String theatrename;
	@NotEmpty(message = "No. of Tickets allotted cannot be empty or null")
	private int NoOfTicketsAllotted;
	@NotEmpty(message = "status cannot be empty or null")
	private String status;

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

	public int getNoOfTicketsAllotted() {
		return NoOfTicketsAllotted;
	}

	public void setNoOfTicketsAllotted(int noOfTicketsAllotted) {
		NoOfTicketsAllotted = noOfTicketsAllotted;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
