package com.dan.imdbapi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.repository.MovieRepository;
import com.dan.imdbapi.repository.MovieTheaterRepository;

/**
 * MoviesService
 */
@Service
public class MoviesService {

	private static final Logger log = LoggerFactory.getLogger(MoviesService.class);

	@Autowired
	private MovieRepository repository;

	@Autowired
	private MovieTheaterRepository theaterRepository;

	public List<Movie> getAll() throws ObjectNotFoundException {
		List<Movie> movies = repository.findAll();
		if (movies.isEmpty())
			throw new ObjectNotFoundException("No movies found");
		return movies;
	}

	public Movie get(String id) throws ObjectNotFoundException {
		Movie movie = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Movie not found"));
		return movie;
	}

	public List<Movie> getMoviesByMovieTheater(String movieTheaterId) throws ObjectNotFoundException {
		List<Movie> movies = theaterRepository.findMoviesById(movieTheaterId)
				.orElseThrow(() -> new ObjectNotFoundException("No movies for this theater"));
		return movies;
	}

	public Movie insert(Movie movie) {
		Movie _movie = repository.insert(movie);
		return _movie;
	}

	public Movie update(Movie movie) {
		Movie _movie = repository.save(movie);
		return _movie;
	}

	public boolean delete(String id) {
		repository.deleteById(id);
		return repository.findById(id).isEmpty();
	}

}