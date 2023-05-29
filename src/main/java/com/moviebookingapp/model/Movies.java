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
	private String theatrename;
	private int NoOfTicketsAllotted;
	private String status;
}
