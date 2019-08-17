package com.dan.imdbapi.controller;

import java.util.List;

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
import com.dan.imdbapi.service.MoviesService;

/**
 * MovieController
 */
@RestController(value = "movie")
@RequestMapping(name = "/movie", value = "/movie")
public class MovieController {

	@Autowired
	private MoviesService service;

	@GetMapping("/{id}")
	public ResponseEntity<Movie> get(@PathVariable("id") String id) {
		try {
			Movie movie = service.get(id);
			return ResponseEntity.status(HttpStatus.OK).body(movie);
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping(value = "")
	public ResponseEntity<List<Movie>> getAll() {
		try {
			List<Movie> movies = service.getAll();
			return ResponseEntity.status(HttpStatus.OK).body(movies);
		} catch (ObjectNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping()
	public ResponseEntity<Movie> insert(@RequestBody Movie movie) {
		Movie _movie = service.insert(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@PutMapping()
	public ResponseEntity<Movie> update(@RequestBody Movie movie) {
		Movie _movie = service.update(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") String id) {
		boolean isDeleted = service.delete(id);
		if (isDeleted)
			return ResponseEntity.status(HttpStatus.OK).build();
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}