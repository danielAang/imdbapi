package com.dan.imdbapi.unit.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.dan.imdbapi.dto.ExhibitionDate;
import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.model.MovieTheater;
import com.dan.imdbapi.repository.MovieTheaterRepository;
import com.dan.imdbapi.service.MovieTheaterService;
import com.dan.imdbapi.service.MoviesService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(MockitoJUnitRunner.class)
public class MovieTheaterServiceTest {
	
	@InjectMocks
	public MovieTheaterService movieTheaterService;
	@Mock
	public MovieTheaterRepository movieTheaterRepository;
	@Mock
	public MoviesService movieService;
	@Mock
	public MovieTheater rosaSilva;
	@Mock
	public Movie lionKing, tedBundy;
	@Mock
	public List<Movie> movies;
	@Mock
	public List<ExhibitionDate> exhibitionDates;
	public List<MovieTheater> movieTheaters;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException {
		this.exhibitionDates = Arrays.asList(new ExhibitionDate(new Date()), new ExhibitionDate(new Date()));
		this.rosaSilva = new MovieTheater("5d585dce83694d125d7cec40", "420818");
		this.lionKing = new Movie("5d585dce83694d125d7cec40", "420818", "O Rei Leão", "");
		this.tedBundy = new Movie("5d585dde83694d125d7cec42", "457799", "Ted Bundy", "");

		this.lionKing.getExhibitionDates().addAll(exhibitionDates);
		this.movies = Arrays.asList(lionKing, tedBundy);
		this.movieTheaters = Arrays.asList(rosaSilva);
	}

	@Test
	public void testGetAll() throws ObjectNotFoundException {
		Mockito.when(movieTheaterRepository.findAll()).thenReturn(movieTheaters);
		movieTheaterService.getAll();
		verify(movieTheaterRepository).findAll();
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void testGetAllObjectNotFound() throws ObjectNotFoundException {
		Mockito.when(movieTheaterRepository.findAll()).thenReturn(Arrays.asList());
		movieTheaterService.getAll();
	}
	
	@Test
	public void testGet() throws ObjectNotFoundException {
		Mockito.when(movieTheaterRepository.findById(Mockito.anyString())).thenReturn(Optional.of(rosaSilva));
		movieTheaterService.get(Mockito.anyString());
		verify(movieTheaterRepository).findById(Mockito.anyString());
	}
	
	@Test(expected = ObjectNotFoundException.class)
	public void testGetObjectNotFound() throws ObjectNotFoundException {
		Mockito.when(movieTheaterRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		movieTheaterService.get(Mockito.anyString());
		verify(movieTheaterRepository).findById(Mockito.anyString());
	}
	
	@Test
	public void testInsert() {
		Mockito.when(movieTheaterRepository.insert(Mockito.any(MovieTheater.class))).thenReturn(rosaSilva);
		movieTheaterService.insert(rosaSilva);
		verify(movieTheaterRepository).insert(rosaSilva);
	}
	
	@Test
	public void testUpdate() {
		Mockito.when(movieTheaterRepository.save(Mockito.any(MovieTheater.class))).thenReturn(rosaSilva);
		movieTheaterService.update(rosaSilva);
		verify(movieTheaterRepository).save(rosaSilva);
	}
	
	@Test
	public void testDelete() {
		Mockito.when(movieTheaterRepository.findById(anyString())).thenReturn(Optional.of(rosaSilva));
		Mockito.doNothing().when(movieTheaterRepository).deleteById(Mockito.anyString());
		movieTheaterService.delete(Mockito.anyString());
		verify(movieTheaterRepository).deleteById(Mockito.anyString());
	}
	
	@Test
	public void testAddMovieToMovieTheater() throws ObjectNotFoundException {
		Mockito.when(movieTheaterRepository.findById(anyString())).thenReturn(Optional.of(rosaSilva));
		Mockito.when(movieService.get(anyString())).thenReturn(lionKing);
		movieTheaterService.addMovieToMovieTheater(new String(), new String());
		verify(movieTheaterRepository).save(rosaSilva);
	}
	
	@Test
	public void testRemoveMovieToMovieTheater() throws ObjectNotFoundException {
		Mockito.when(movieTheaterRepository.findById(anyString())).thenReturn(Optional.of(rosaSilva));
		movieTheaterService.removeMovieFromMovieTheater(new String(), new String());
		verify(movieTheaterRepository).save(rosaSilva);
	}
	
	@Test
	public void testAddExibithionDate() throws ObjectNotFoundException {
		MovieTheater movieTheater = Mockito.spy(MovieTheater.class);
		Mockito.when(movieTheater.getMovies()).thenReturn(movies);
		Mockito.when(movieTheaterRepository.findById(anyString())).thenReturn(Optional.of(movieTheater));

		movieTheaterService.addExibithionDate(rosaSilva.getId(), lionKing.getInternalId(), exhibitionDates);
		verify(movieTheaterRepository).save(movieTheater);
		Assert.assertFalse(lionKing.getExhibitionDates().isEmpty());
	}
	
	@Test
	public void testRemoveExibithionDate() throws ObjectNotFoundException {
		MovieTheater movieTheater = Mockito.spy(MovieTheater.class);
		Mockito.when(movieTheater.getMovies()).thenReturn(movies);
		Mockito.when(movieTheaterRepository.findById(anyString())).thenReturn(Optional.of(movieTheater));

		movieTheaterService.removeExibithionDate(rosaSilva.getId(), lionKing.getInternalId(), exhibitionDates);
		verify(movieTheaterRepository).save(movieTheater);
		Assert.assertTrue(lionKing.getExhibitionDates().isEmpty());
	}
}
