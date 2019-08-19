package com.dan.imdbapi.service;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dan.imdbapi.dto.ExhibitionDate;
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
		log.info("Retrieving all MovieTheaters");
		return movies;
	}

	public MovieTheater get(String id) throws ObjectNotFoundException {
		MovieTheater movie = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Theaters not found"));
		log.info(String.format("Retrieving MovieTheater %s", id));
		return movie;
	}

	public MovieTheater insert(MovieTheater movieTheater) {
		movieTheater.setCreatedAt(new Date());
		MovieTheater _movieTheater = repository.insert(movieTheater);
		log.info(String.format("MovieTheater inserted with id %s", _movieTheater.getId()));
		return _movieTheater;
	}

	public MovieTheater update(MovieTheater movieTheater) {
		movieTheater.setUpdatedAt(new Date());
		MovieTheater _movieTheater = repository.save(movieTheater);
		log.info(String.format("MovieTheater %s updated", _movieTheater.getId()));
		return _movieTheater;
	}

	public boolean delete(String id) {
		if (!repository.findById(id).isPresent()) {
			log.info(String.format("Attempt to delete MovieTheater %s, but none was found by given id", id));
			return false;
		} else {
			repository.deleteById(id);
			log.info(String.format("MovieTheater %s deleted", id));
			return true;
		}
	}

	public void addMovieToMovieTheater(String movieTheaterId, String movieInternalId) throws ObjectNotFoundException {
		Optional<MovieTheater> optional = repository.findById(movieTheaterId);
		if (optional.isPresent()) {
			MovieTheater movieTheater = optional.get();
			Movie movie = movieService.get(movieInternalId);
			if (!movieTheater.getMovies().contains(movie)) {
				movieTheater.getMovies().add(movie);
				repository.save(movieTheater);
			}
		}
	}

	public void removeMovieFromMovieTheater(String movieTheaterId, String movieInternalId)
			throws ObjectNotFoundException {
		Optional<MovieTheater> optional = repository.findById(movieTheaterId);
		if (optional.isPresent()) {
			MovieTheater movieTheater = optional.get();
			ListIterator<Movie> listIterator = movieTheater.getMovies().listIterator();
			while (listIterator.hasNext()) {
				Movie movie = listIterator.next();
				if (movieInternalId.equals(movie.getInternalId())) {
					listIterator.remove();
					break;
				}
			}
			repository.save(movieTheater);
		}
	}

	public void addExibithionDate(String movieTheaterId, String movieInternalId, List<ExhibitionDate> dates)
			throws ObjectNotFoundException {
		Optional<MovieTheater> optional = repository.findById(movieTheaterId);
		if (optional.isPresent()) {
			log.info("Movie found");
			MovieTheater movieTheater = optional.get();
			movieTheater.getMovies().forEach(m -> {
				if (movieInternalId.equals(m.getInternalId())) {
					m.setExhibitionDates(dates);
					log.info(String.format("Adding exhibition date to Movie %s", m.getInternalId()));
				}
			});
			repository.save(movieTheater);
		}
	}

	public void removeExibithionDate(String movieTheaterId, String movieInternalId, List<ExhibitionDate> dates)
			throws ObjectNotFoundException {
		Optional<MovieTheater> optional = repository.findById(movieTheaterId);
		if (optional.isPresent()) {
			MovieTheater movieTheater = optional.get();
			Optional<Movie> optionalMovie = movieTheater.getMovies().stream().filter(m -> movieInternalId.equals(m.getInternalId()))
					.findFirst();
			if (optionalMovie.isPresent()) {
				Movie movie = optionalMovie.get();
				movie.getExhibitionDates().removeAll(dates);
			}
			repository.save(movieTheater);
		}
	}

}