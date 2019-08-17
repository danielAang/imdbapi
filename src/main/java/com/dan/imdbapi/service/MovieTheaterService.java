package com.dan.imdbapi.service;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.model.MovieTheater;
import com.dan.imdbapi.repository.MovieTheaterRepository;

/**
 * MovieTheaterService
 */
@Service
public class MovieTheaterService {

	private static final Logger log = LoggerFactory.getLogger(MovieTheaterService.class);

	@Autowired
	private MovieTheaterRepository repository;
	
	@Autowired
	private MoviesService movieService;

	public List<MovieTheater> getAll() throws ObjectNotFoundException {
		List<MovieTheater> movies = repository.findAll();
		if (movies.isEmpty())
			throw new ObjectNotFoundException("No theaters found");
		return movies;
	}

	public MovieTheater get(String id) throws ObjectNotFoundException {
		MovieTheater movie = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Theaters not found"));
		return movie;
	}

	public MovieTheater insert(MovieTheater movie) {
		MovieTheater _movie = repository.insert(movie);
		return _movie;
	}

	public MovieTheater update(MovieTheater movie) {
		MovieTheater _movie = repository.save(movie);
		return _movie;
	}

	public boolean delete(String id) {
		repository.deleteById(id);
		return repository.findById(id).isEmpty();
	}
	
	public void addMovieToMovieTheater(String movieTheaterId, String internalId) throws ObjectNotFoundException {
		Optional<MovieTheater> optional = repository.findById(movieTheaterId);
		if (optional.isPresent()) {
			Movie movie = movieService.get(internalId);
			MovieTheater movieTheater = optional.get();
			movieTheater.getMovies().add(movie);
			repository.save(movieTheater);
		}
	}
	
	public void removeMovieFromMovieTheater(String movieTheaterId, String internalId) throws ObjectNotFoundException {
		Optional<MovieTheater> optional = repository.findById(movieTheaterId);
		if (optional.isPresent()) {
			MovieTheater movieTheater = optional.get();
			ListIterator<Movie> listIterator = movieTheater.getMovies().listIterator();
			while (listIterator.hasNext()) {
				Movie movie = listIterator.next();
				if (movie.getInternalId().equals(internalId)) {
					listIterator.remove();
					break;
				}
			}
			repository.save(movieTheater);
		}
	}

}