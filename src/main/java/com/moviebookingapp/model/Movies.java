package com.moviebookingapp.model;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
//@CompoundIndexes({
//		@CompoundIndex(name = "composite_index", def = "{'moviename' : 1, 'theatrename' : 1}", unique = false) })
@Document
public class Movies implements Serializable{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3199299007836747186L;
	private String _id;
	private String moviename;
	private String theatrename;
	private int NoOfTicketsAllotted;
	private String status;
}
