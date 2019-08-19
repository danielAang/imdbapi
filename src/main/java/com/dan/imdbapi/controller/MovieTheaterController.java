package com.dan.imdbapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dan.imdbapi.dto.ExhibitionDate;
import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.model.MovieTheater;
import com.dan.imdbapi.service.MovieTheaterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * MovieTheater
 */
@Api(value = "Operations related to MovieTheater entity")
@RestController(value = "movietheater")
@RequestMapping(name = "/movietheater", value = "/movietheater", consumes = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_JSON_UTF8_VALUE })
public class MovieTheaterController {

	@Autowired
	private MovieTheaterService service;

	@ApiOperation(value = "Retrieves a MovieTheater for a given id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieTheater> get(@PathVariable("id") String id) throws ObjectNotFoundException {
		MovieTheater movie = service.get(id);
		return ResponseEntity.status(HttpStatus.OK).body(movie);
	}

	@ApiOperation(value = "Retrieves all MovieTheater")
	@GetMapping(value = "")
	public ResponseEntity<List<MovieTheater>> getAll() throws ObjectNotFoundException {
		List<MovieTheater> movies = service.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(movies);
	}

	@ApiOperation(value = "Insert a given MovieTheater")
	@PostMapping()
	public ResponseEntity<MovieTheater> insert(@RequestBody MovieTheater movie) {
		MovieTheater _movie = service.insert(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@ApiOperation(value = "Updates a given MovieTheater")
	@PutMapping()
	public ResponseEntity<MovieTheater> update(@RequestBody MovieTheater movie) {
		MovieTheater _movie = service.update(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@ApiOperation(value = "Deletes a given MovieTheater")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id", required = true) String id) {
		boolean isDeleted = service.delete(id);
		if (isDeleted)
			return ResponseEntity.status(HttpStatus.OK).build();
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@ApiOperation(value = "Retrieves a list of Movie for a given MovieTheater")
	@GetMapping(value = "/{id}/movies")
	public ResponseEntity<List<Movie>> getMoviesFromMovieTheater(@PathVariable("id") String id)
			throws ObjectNotFoundException {
		MovieTheater movieTheater = service.get(id);
		return ResponseEntity.status(HttpStatus.OK).body(movieTheater.getMovies());
	}

	@ApiOperation(value = "Associates a Movie to a MovieTheater")
	@PutMapping(value = "/{id}/addMovie/{internalId}")
	public ResponseEntity<Void> addMovieToMovieTheater(@PathVariable("id") String movieTheaterId,
			@PathVariable("internalId") String movieId) throws ObjectNotFoundException {
		service.addMovieToMovieTheater(movieTheaterId, movieId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "Detach a Movie from a MovieTheater")
	@PutMapping(value = "/{id}/removeMovie/{internalId}")
	public ResponseEntity<MovieTheater> removeMovieToMovieTheater(@PathVariable("id") String movieTheaterId,
			@PathVariable("internalId") String movieId) throws ObjectNotFoundException {
		service.removeMovieFromMovieTheater(movieTheaterId, movieId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "Set Exhibition dates to a Movie from a MovieTheater")
	@PutMapping(value = "/{id}/movie/{movieInternalId}/setDates")
	public ResponseEntity<Void> addExibithionDate(@PathVariable("id") String movieTheaterId,
			@PathVariable("movieInternalId") String movieInternalId, @RequestBody List<ExhibitionDate> dates)
			throws ObjectNotFoundException {
		service.addExibithionDate(movieTheaterId, movieInternalId, dates);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ApiOperation(value = "Remove Exhibition dates from a Movie from a MovieTheater")
	@PutMapping(value = "/{id}/movie/{movieInternalId}/removeDates")
	public ResponseEntity<Void> removeExibithionDate(@PathVariable("id") String movieTheaterId,
			@PathVariable("movieInternalId") String movieInternalId, @Valid @RequestBody List<ExhibitionDate> dates)
			throws ObjectNotFoundException {
		service.removeExibithionDate(movieTheaterId, movieInternalId, dates);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}