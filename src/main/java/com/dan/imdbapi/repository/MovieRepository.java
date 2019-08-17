package com.dan.imdbapi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dan.imdbapi.model.Movie;

/**
 * MovieRepository
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, String> {
	
	Optional<Movie> findByApiId(String apiId);

}