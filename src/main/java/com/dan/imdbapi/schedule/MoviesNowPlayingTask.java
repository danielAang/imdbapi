package com.dan.imdbapi.schedule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.dan.imdbapi.model.Movie;
import com.dan.imdbapi.repository.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MoviesNowPlayingTask {

	@Autowired
	private MovieRepository movieRepository;

	@Value("${moviedb.url.now_playing}")
	private String moviesNowPlaying;

	@Value("${moviedb.secret}")
	private String moviedbSecret;

	private static final Logger log = LoggerFactory.getLogger(MoviesNowPlayingTask.class);

	@Scheduled(zone = "America/Recife", fixedDelay = 600000, initialDelay = 3000)
	public void getMovies() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		log.info("Iniciando tarefa agendada em " + formatter.format(new Date()));
		Integer currentPage = 1;
		Integer totalPages = 1000;
		RestTemplate restTemplate = new RestTemplate();
		String format = "%s?api_key%s&language=pt-BR&page=%s&region=BR";
		String url = String.format(format, moviesNowPlaying, moviedbSecret, currentPage);

		try {
			do {
				log.info("Acessando url: " + url);
				ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
				if (HttpStatus.OK.equals(response.getStatusCode())) {
					JSONObject data = response.hasBody() ? new JSONObject(response.getBody()) : null;
					if (data == null)
						break;
					JSONArray results = data.getJSONArray("results");
					List<Movie> movies = getMoviesFromResult(results);
					checkAndSaveMovies(movies);
					totalPages = data.getInt("total_pages");
					currentPage += 1;
					url = String.format(format, moviesNowPlaying, moviedbSecret, currentPage);
				} else {
					break;
				}
				if (totalPages > 1000)
					break;
				Thread.sleep(20000);
			} while (currentPage <= totalPages);
		} catch (Exception e) {
			log.error("Falha na execução da tarefa agendada: " + e.getMessage());
		}
		log.info("Tarefa concluída em: " + formatter.format(new Date()));
	}

	private List<Movie> getMoviesFromResult(JSONArray results) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Movie> movies = mapper.readValue(results.toString(), new TypeReference<List<Movie>>() {
			});
			return movies;
		} catch (IOException e) {
			log.error("Erro ao transformar jsonArray em List<Movie>.: " + e.getMessage());
		}
		return null;
	}

	private void checkAndSaveMovies(List<Movie> movies) {
		for (Movie movie : movies) {
			try {
				Optional<Movie> optional = movieRepository.findByApiId(movie.getApiId());
				if (optional.isEmpty()) {
					Movie _movie = movieRepository.insert(movie);
					log.info("Movie [" + _movie.getTitle() + "] cadastrado com id: " + _movie.getInternalId());
				}
			} catch (Exception e) {
				log.error("Falha ao tratar filmes: " + e.getMessage());
			}
		}
	}
}
