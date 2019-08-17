package com.dan.imdbapi.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

	@Id
	@JsonProperty("internalId")	
	private String internalId;

	@JsonProperty("id")
	private String apiId;

	@JsonProperty("title")
	private String title;

	@JsonProperty("overview")
	private String overview;

	@JsonProperty("poster_path")
	private String posterPath;

	@JsonProperty("vote_average")
	private Long voteAverage;

	@JsonProperty("original_language")
	private String originalLanguage;

	@JsonProperty("original_title")
	private String originalTitle;

	@JsonProperty("backdrop_path")
	private String backdropPath;

	@JsonProperty("release_date")
	private Date releaseDate;

	@JsonProperty("genres")
	private List<Genre> genres;

	@JsonProperty("genre_ids")
	private List<Integer> genreIds;

	@JsonProperty("production_countries")
	private List<ProductionCountry> productionCountries;

	@JsonProperty("spoken_languages")
	private List<SpokenLanguages> spokenLanguages;

	private List<Date> exibithions;

	public String getInternalId() {
		return internalId;
	}

	public String getApiId() {
		return apiId;
	}

	public void setApiId(String apiId) {
		this.apiId = apiId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}

	public Long getVoteAverage() {
		return voteAverage;
	}

	public void setVoteAverage(Long voteAverage) {
		this.voteAverage = voteAverage;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<Genre> getGenres() {
		return genres;
	}

	public void setGenres(List<Genre> genres) {
		this.genres = genres;
	}

	public List<Integer> getGenreIds() {
		return genreIds;
	}

	public void setGenreIds(List<Integer> genreIds) {
		this.genreIds = genreIds;
	}

	public List<ProductionCountry> getProductionCountries() {
		return productionCountries;
	}

	public void setProductionCountries(List<ProductionCountry> productionCountries) {
		this.productionCountries = productionCountries;
	}

	public List<SpokenLanguages> getSpokenLanguages() {
		return spokenLanguages;
	}

	public void setSpokenLanguages(List<SpokenLanguages> spokenLanguages) {
		this.spokenLanguages = spokenLanguages;
	}

	public List<Date> getExibithions() {
		return exibithions;
	}

	public void setExibithions(List<Date> exibithions) {
		this.exibithions = exibithions;
	}

	@Override
	public String toString() {
		return "Movie [id=" + internalId + ", title=" + title + "]";
	}

}