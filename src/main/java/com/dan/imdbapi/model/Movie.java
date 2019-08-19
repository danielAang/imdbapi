package com.dan.imdbapi.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dan.imdbapi.dto.ExhibitionDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document
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

	private List<ExhibitionDate> exhibitionDates;

	public Movie() {

	}

	public Movie(String apiId, String title, String overview) {
		super();
		this.apiId = apiId;
		this.title = title;
		this.overview = overview;
	}

	public Movie(String internalId, String apiId, String title, String overview) {
		super();
		this.internalId = internalId;
		this.apiId = apiId;
		this.title = title;
		this.overview = overview;
	}

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

	public List<ExhibitionDate> getExhibitionDates() {
		return exhibitionDates;
	}

	public void setExhibitionDates(List<ExhibitionDate> exibithions) {
		this.exhibitionDates = exibithions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((internalId == null) ? 0 : internalId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		if (internalId == null) {
			if (other.internalId != null)
				return false;
		} else if (!internalId.equals(other.internalId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movie [id=" + internalId + ", title=" + title + "]";
	}

}