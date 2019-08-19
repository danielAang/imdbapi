package com.dan.imdbapi.schedule;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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

/**
 * Class that handles the job of getting movies from the imdb api. This class
 * runs by schedule, starting 3seg after application startup and runs again
 * 10min after last completed execution
 * 
 * @author daniel
 *
 */
@Component
public class MoviesNowPlayingTask {

	@Autowired
	private MovieRepository movieRepository;
	@Value("${moviedb.url.now_playing}")
	private String moviesNowPlaying;
	@Value("${moviedb.secret}")

	private String moviedbSecret;
	private RestTemplate restTemplate = new RestTemplate();
	private static final String URL = "%s?api_key%s&language=pt-BR&page=%s&region=BR";
	private Integer currentPage = 1;
	private Integer totalPages = 1000;

	private static final Logger log = LoggerFactory.getLogger(MoviesNowPlayingTask.class);

	@Scheduled(zone = "America/Recife", fixedDelay = 600000, initialDelay = 3000)
	public void getMovies() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		log.info(String.format("Starting scheduled task at %s", formatter.format(new Date())));
		String url = String.format(URL, moviesNowPlaying, moviedbSecret, currentPage);

		try {
			do {
				log.info(String.format("Accessing url: %s", url));
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
					url = String.format(URL, moviesNowPlaying, moviedbSecret, currentPage);
				} else {
					break;
				}
				// The api documentation limits the amount of pages to 1000
				if (totalPages > 1000)
					break;
				// Due to api request limitation, set Thread.sleep to avoid problems
				Thread.sleep(20000);
			} while (currentPage <= totalPages);
		} catch (Exception e) {
			log.error(String.format("Execution of scheduled task failed at %s: ", e.getMessage()));
		}
		log.info(String.format("Scheduled task endeded at: %s", formatter.format(new Date())));
	}

	/**
	 * Convert a json array with movies to a list of movies
	 * 
	 * @param results JSONArray containing data from imdb api
	 * @return A list of movies or a empty array in case of error
	 */
	private List<Movie> getMoviesFromResult(JSONArray results) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Movie> movies = mapper.readValue(results.toString(), new TypeReference<List<Movie>>() {
			});
			return movies;
		} catch (Exception e) {
			log.error(String.format("Error during List<Movie> transformation"), e);
		}
		return Arrays.asList();
	}

	/**
	 * Save movies only if they already aren't on database
	 * 
	 * @param movies List of movies from the api
	 */
	private void checkAndSaveMovies(List<Movie> movies) {
		for (Movie movie : movies) {
			try {
				Optional<Movie> optional = movieRepository.findByApiId(movie.getApiId());
				if (!optional.isPresent()) {
					Movie insertedMovie = movieRepository.insert(movie);
					log.info(String.format("Movie [%s] inserted with id: ", movie.getTitle(), insertedMovie.getInternalId()));
				} else {
					log.info(String.format("Movie [%s] already inserted", movie.getTitle()));
				}
			} catch (Exception e) {
				log.error(String.format("Failing when saving movies: %s", e.getMessage()));
			}
		}
	}
}
