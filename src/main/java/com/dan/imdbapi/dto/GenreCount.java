package com.dan.imdbapi.dto;

public class GenreCount {

	private Integer genreId;
	private Integer count;

	public GenreCount() {
		super();
	}

	public Integer getGenreId() {
		return genreId;
	}

	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
