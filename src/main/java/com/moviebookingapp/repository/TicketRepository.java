package com.moviebookingapp.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.model.Ticket;

@Repository
public interface TicketRepository extends MongoRepository<Ticket, String>{
	@Query("{moviename:?0}")
	List<Ticket> findByMovieName(String moviename);
}
