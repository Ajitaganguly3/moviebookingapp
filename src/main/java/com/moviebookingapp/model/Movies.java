package com.moviebookingapp.model;

import java.io.Serializable;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Movies implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4188335777590705816L;
	private String _id;
	private String moviename;
	private String genre;
	private String releaseDate;
	private String theatrename;
	private String price;
	private int NoOfTicketsAllotted;
	private String status;
	private String about;
	private String posterURL;

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
