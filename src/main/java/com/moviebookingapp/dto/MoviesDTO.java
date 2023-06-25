package com.moviebookingapp.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class MoviesDTO implements Serializable {

	private static final long serialVersionUID = -1151637347452772181L;

	@NotEmpty(message = "Movie Name cannot be empty or null")
	private String moviename;
	@NotEmpty(message = "Genre cannot be empty or null")
	private String genre;
	@NotEmpty(message = "Release Date cannot be empty or null")
	private String releaseDate;
	@NotEmpty(message = "Theatre Name cannot be empty or null")
	private String theatrename;
	@NotEmpty(message = "Price cannot be empty or null")
	private String price;
	@PositiveOrZero(message = "No. of Tickets alloted cannot be negative")
	private int NoOfTicketsAllotted;
	@NotEmpty(message = "status cannot be empty or null")
	private String status;
	@NotEmpty(message = "About cannot be empty or null")
	private String about;
	@NotEmpty(message = "Poster URL cannot be empty or null")
	private String posterURL;

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
