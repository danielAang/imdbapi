package com.dan.imdbapi.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.service.MovieTheaterService;
import com.dan.imdbapi.service.MoviesService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@RunWith(SpringRunner.class)
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MoviesService movieService;

	@MockBean
	private MovieTheaterService movieTheaterService;
	
	@Value("classpath:movie.json")
	private Resource movieJSON;

	private List<Movie> movies;
	private Movie lionKing;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		lionKing = mapper.readValue(movieJSON.getInputStream(), Movie.class);
		movies = List.of(lionKing);
	}

	@Test
	public void testGet() throws Exception {
		Mockito.when(movieService.get(lionKing.getInternalId())).thenReturn(lionKing);
		mockMvc.perform(get("/movie/" + lionKing.getInternalId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.internalId", is(lionKing.getInternalId())));
	}

	@Test
	public void testGetAll() throws Exception {
		Mockito.when(movieService.getAll()).thenReturn(movies);
		mockMvc.perform(get("/movie").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetAllNoneWasFound() throws Exception {
		Mockito.when(movieService.getAll()).thenThrow(ObjectNotFoundException.class);
		mockMvc.perform(get("/movie").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testPost() throws Exception {
		JSONObject js = new JSONObject(lionKing);
		Mockito.when(movieService.insert(Mockito.any(Movie.class))).thenReturn(lionKing);
		mockMvc.perform(post("/movie")
			.contentType(MediaType.APPLICATION_JSON)
			.content(js.toString())
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.internalId", is(lionKing.getInternalId())));
	}
	
	@Test
	public void testPut() throws Exception {
		JSONObject js = new JSONObject(lionKing);
		Mockito.when(movieService.update(Mockito.any(Movie.class))).thenReturn(lionKing);
		mockMvc.perform(put("/movie")
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON)
			.content(js.toString()))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testDelete() throws Exception {
		Mockito.when(movieService.delete(Mockito.anyString())).thenReturn(true);
		mockMvc.perform(delete("/movie/{id}", lionKing.getApiId())
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
}