package com.moviebookingapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.moviebookingapp.model.UserProfile;

@Repository
public interface UserProfileRespository extends MongoRepository<UserProfile, String> {
	
	Optional<List<UserProfile>> findByFirstName(String firstName);

}
