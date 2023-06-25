package com.moviebookingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.model.Movies;

@Repository
public interface MoviesRepository extends MongoRepository<Movies, String> {

	// Movies findbyMovienameAndTheatrename(String moviename, String theatrename);

	List<Movies> findAllByOrderByMovienameAsc();

	List<Movies> findByMovienameContainingIgnoreCaseOrderByMovienameAsc(String moviename);

	Optional<Movies> findByMoviename(String moviename);
	// Optional<Movies> findByMovienameOrderByMovienameAsc(String moviename);

	Optional<Movies> findByMovienameOrderByMovienameAsc(String moviename);

//	Movies findByMoviename(String moviename);

}
