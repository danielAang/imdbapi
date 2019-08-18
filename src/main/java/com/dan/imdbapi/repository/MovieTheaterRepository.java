package com.dan.imdbapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.dan.imdbapi.model.MovieTheater;

/**
 * MovieTheaterRepository
 */
@Repository
public interface MovieTheaterRepository extends MongoRepository<MovieTheater, String> {


}