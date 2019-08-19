package com.dan.imdbapi.unit.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
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

import com.dan.imdbapi.dto.ExhibitionDate;
import com.dan.imdbapi.exception.ObjectNotFoundException;
import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.model.MovieTheater;
import com.dan.imdbapi.service.MovieTheaterService;
import com.dan.imdbapi.service.MoviesService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
@RunWith(SpringRunner.class)
public class MovieTheaterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MoviesService movieService;

	@MockBean
	private MovieTheaterService movieTheaterService;
	
	@Value("classpath:movieTheater.json")
	private Resource movieTheaterJSON;
	
	@Value("classpath:movie.json")
	private Resource movieJSON;

	public List<ExhibitionDate> exhibitionDates;
	private List<MovieTheater> movieTheaters;
	private MovieTheater roseSilva;
	private Movie lionKing;

	@Before
	public void before() throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		exhibitionDates = Arrays.asList(new ExhibitionDate(new Date()), new ExhibitionDate(new Date()));
		roseSilva = mapper.readValue(movieTheaterJSON.getInputStream(), MovieTheater.class);
		lionKing = mapper.readValue(movieJSON.getInputStream(), Movie.class);
		movieTheaters = Arrays.asList(roseSilva);
	}

	@Test
	public void testGet() throws Exception {
		Mockito.when(movieTheaterService.get(anyString())).thenReturn(roseSilva);
		mockMvc.perform(get("/movietheater/" + roseSilva.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(roseSilva.getId())));
	}

	@Test
	public void testGetAll() throws Exception {
		Mockito.when(movieTheaterService.getAll()).thenReturn(movieTheaters);
		mockMvc.perform(get("/movietheater").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testGetAllNoneWasFound() throws Exception {
		Mockito.when(movieTheaterService.getAll()).thenThrow(ObjectNotFoundException.class);
		mockMvc.perform(get("/movietheater").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNotFound())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}
	
	@Test
	public void testPost() throws Exception {
		JSONObject js = new JSONObject(roseSilva);
		Mockito.when(movieTheaterService.insert(Mockito.any(MovieTheater.class))).thenReturn(roseSilva);
		mockMvc.perform(post("/movietheater")
			.contentType(MediaType.APPLICATION_JSON)
			.content(js.toString())
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(roseSilva.getId())));
	}
	
	@Test
	public void testPut() throws Exception {
		JSONObject js = new JSONObject(roseSilva);
		Mockito.when(movieTheaterService.update(Mockito.any(MovieTheater.class))).thenReturn(roseSilva);
		mockMvc.perform(put("/movietheater")
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON)
			.content(js.toString()))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testDelete() throws Exception {
		Mockito.when(movieTheaterService.delete(Mockito.anyString())).thenReturn(true);
		mockMvc.perform(delete("/movietheater/{id}", roseSilva.getId())
			.contentType(MediaType.APPLICATION_JSON)
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}
	
	@Test
	public void testAddExibithionDate() throws Exception {
		String jsonArray =  "[{\"exhibition\": \"19/08/2019 14:11\"},{\"exhibition\": \"19/08/2019 18:11\"},{\"exhibition\": \"20/08/2019 10:00\"}]";
		Mockito.doNothing().when(movieTheaterService).addExibithionDate(anyString(), anyString(), Mockito.any());
		mockMvc.perform(put("/movietheater/{id}/movie/{movieInternalId}/setDates", roseSilva.getId(), lionKing.getInternalId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonArray.toString())
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
	
	@Test
	public void testRemoveExibithionDate() throws Exception {
		String jsonArray =  "[{\"exhibition\": \"19/08/2019 14:11\"},{\"exhibition\": \"19/08/2019 18:11\"},{\"exhibition\": \"20/08/2019 10:00\"}]";
		Mockito.doNothing().when(movieTheaterService).removeExibithionDate(anyString(), anyString(), Mockito.any());
		mockMvc.perform(put("/movietheater/{id}/movie/{movieInternalId}/removeDates", roseSilva.getId(), lionKing.getInternalId())
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonArray)
			.characterEncoding("UTF-8")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());
	}
}