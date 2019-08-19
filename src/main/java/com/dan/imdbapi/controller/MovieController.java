package com.dan.imdbapi.controller;

import java.util.List;

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

import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.service.MoviesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * MovieController
 */
@Api(value = "Operations related to Movie entity")
@RestController(value = "movie")
@RequestMapping(name = "/movie", value = "/movie", consumes = { MediaType.APPLICATION_JSON_VALUE,
		MediaType.APPLICATION_JSON_UTF8_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
				MediaType.APPLICATION_JSON_UTF8_VALUE })
public class MovieController {

	@Autowired
	private MoviesService service;

	@ApiOperation(value = "Retrieves a Movie for a given id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Movie> get(@PathVariable("id") String id) throws ObjectNotFoundException {
		Movie movie = service.get(id);
		return ResponseEntity.status(HttpStatus.OK).body(movie);
	}

	@ApiOperation(value = "Retrieves all Movies")
	@GetMapping(value = "")
	public ResponseEntity<List<Movie>> getAll() throws ObjectNotFoundException {
		List<Movie> movies = service.getAll();
		return ResponseEntity.status(HttpStatus.OK).body(movies);
	}

	@ApiOperation(value = "Insert a given Movie")
	@PostMapping()
	public ResponseEntity<Movie> insert(@RequestBody Movie movie) {
		Movie _movie = service.insert(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@ApiOperation(value = "Updates a given Movies")
	@PutMapping()
	public ResponseEntity<Movie> update(@RequestBody Movie movie) {
		Movie _movie = service.update(movie);
		return ResponseEntity.status(HttpStatus.OK).body(_movie);
	}

	@ApiOperation(value = "Deletes a given Movie")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable(value = "id", required = true) String id) {
		boolean isDeleted = service.delete(id);
		if (isDeleted)
			return ResponseEntity.status(HttpStatus.OK).build();
		else
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}