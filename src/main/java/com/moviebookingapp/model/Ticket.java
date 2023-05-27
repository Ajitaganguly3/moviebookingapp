package com.moviebookingapp.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Ticket {

	@Id
	private String _id;

	private String moviename;
	private String theatrename;
	private int noOfTickets;
	private List<String> seatnumber;

}
