package com.dan.imdbapi.service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.dan.imdbapi.dto.GenreCount;
import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.repository.MovieRepository;

/**
 * MoviesService
 */
@Service
public class MoviesService {

	private static final Logger log = LoggerFactory.getLogger(MoviesService.class);

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<Movie> getAll() throws ObjectNotFoundException {
		List<Movie> movies = repository.findAll();
		if (movies.isEmpty())
			throw new ObjectNotFoundException("No movies found");
		log.info("Retrieving all Movies");
		return movies;
	}

	public Movie get(String id) throws ObjectNotFoundException {
		Movie movie = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Movie not found"));
		log.info(String.format("Retrieving Movie %s", id));
		return movie;
	}

	public Movie insert(Movie movie) {
		Movie _movie = repository.insert(movie);
		log.info(String.format("Movie inserted with id %s", _movie.getInternalId()));
		return _movie;
	}

	public Movie update(Movie movie) {
		Movie _movie = repository.save(movie);
		log.info(String.format("Movie %s updated", _movie.getInternalId()));
		return _movie;
	}

	public boolean delete(String id) {
		if (repository.findById(id).isPresent()) {
			log.info(String.format("Attempt to delete Movie %s, but none was found by given id", id));
			return false;
		} else {
			repository.deleteById(id);
			log.info(String.format("Movie %s deleted", id));
			return true;
		}
	}

	public List<GenreCount> countCategories() {
		Aggregation agg = newAggregation(
				unwind("genreIds"),
			    group("genreIds").count().as("count"),
			    project("count").and("genreId").previousOperation(),
			    sort(new Sort(Direction.DESC, "count"))
			);
		AggregationResults<GenreCount> results = mongoTemplate.aggregate(agg, "movie", GenreCount.class);
		List<GenreCount> genreCount = results.getMappedResults();
		return genreCount;
	}
}