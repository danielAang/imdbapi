package com.dan.imdbapi.unit.service;

import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.repository.MovieRepository;
import com.dan.imdbapi.service.MoviesService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceTest {

	@InjectMocks
	private MoviesService moviesService;

	@Mock
	private MovieRepository movieRepository;

	@Value("classpath:movie.json")
	private Resource movieJSON;

	@Value("classpath:movie2.json")
	private Resource movieJSON2;

	private List<Movie> movies;
	private Movie lionKing, onceUponATime;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException {
		lionKing = new Movie("5d585dce83694d125d7cec40", "420818", "O Rei Leão", "");
		onceUponATime = new Movie("5d585dde83694d125d7cec41", "466272", "Era uma vez em Hollywood", "");
		movies = Arrays.asList(lionKing, onceUponATime);
	}

	@Test
	public void testGetAll() throws ObjectNotFoundException {
		Mockito.when(movieRepository.findAll()).thenReturn(movies);
		moviesService.getAll();
		verify(movieRepository).findAll();
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void testGetAllObjectNotFound() throws ObjectNotFoundException {
		Mockito.when(movieRepository.findAll()).thenReturn(Arrays.asList());
		moviesService.getAll();
	}
	
	@Test
	public void testGet() throws ObjectNotFoundException {
		Mockito.when(movieRepository.findById(Mockito.anyString())).thenReturn(Optional.of(lionKing));
		moviesService.get(Mockito.anyString());
		verify(movieRepository).findById(Mockito.anyString());
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void testGetObjectNotFound() throws ObjectNotFoundException {
		Mockito.when(movieRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		moviesService.get(Mockito.anyString());
		verify(movieRepository).findById(Mockito.anyString());
	}
	
	@Test
	public void testInsert() {
		Mockito.when(movieRepository.insert(Mockito.any(Movie.class))).thenReturn(lionKing);
		moviesService.insert(lionKing);
		verify(movieRepository).insert(lionKing);
	}
	
	@Test
	public void testUpdate() {
		Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(lionKing);
		moviesService.update(lionKing);
		verify(movieRepository).save(lionKing);
	}
	
	@Test
	public void testDelete() {
		Mockito.doNothing().when(movieRepository).deleteById(Mockito.anyString());
		moviesService.delete(Mockito.anyString());
		verify(movieRepository).deleteById(Mockito.anyString());
	}
}
