package com.dan.imdbapi.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.model.MovieTheater;
import com.dan.imdbapi.service.MovieTheaterService;

/**
 * MovieTheater
 */
@RestController
@RequestMapping(value = "/movietheater")
public class MovieTheaterController {

	@Autowired
	private MovieTheaterService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieTheater> get(@PathParam("id") String id) {
		try {
			MovieTheater movie = service.get(id);
			return ResponseEntity.status(HttpStatus.OK).body(movie);
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(value = "")
	public ResponseEntity<List<MovieTheater>> getAll() {
		try {
			List<MovieTheater> movies = service.getAll();
			return ResponseEntity.status(HttpStatus.OK).body(movies);
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping()
	public ResponseEntity<MovieTheater> insert(@RequestBody MovieTheater movie) {
		MovieTheater _movie = service.insert(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@PutMapping()
	public ResponseEntity<MovieTheater> update(@RequestBody MovieTheater movie) {
		MovieTheater _movie = service.update(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathParam("id") String id) {
		boolean isDeleted = service.delete(id);
		if (isDeleted)
			return ResponseEntity.status(HttpStatus.OK).build();
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}

	@GetMapping(value = "/{id}/movies")
	public ResponseEntity<List<Movie>> getMoviesFromMovieTheater(@PathVariable("id") String id) {
		try {
			MovieTheater movieTheater = service.get(id);
			return ResponseEntity.status(HttpStatus.OK).body(movieTheater.getMovies());
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PutMapping(value = "/{id}/add_movie/{movieId}")
	public ResponseEntity<Void> addMovieToMovieTheater(@PathVariable("id") String movieTheaterId,
			@PathVariable("movieId") String movieId) {
		try {
			service.addMovieToMovieTheater(movieTheaterId, movieId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@PutMapping(value = "/{id}/remove_movie/{movieId}")
	public ResponseEntity<MovieTheater> removeMovieToMovieTheater(@PathVariable("id") String movieTheaterId,
			@PathVariable("movieId") String movieId) {
		try {
			service.removeMovieFromMovieTheater(movieTheaterId, movieId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

}