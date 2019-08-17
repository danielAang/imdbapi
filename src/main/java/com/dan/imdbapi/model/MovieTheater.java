package com.dan.imdbapi.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * MovieTheater
 */
public class MovieTheater {

	@Id
	private String id;

	private String name;

	private List<Movie> movies;

	private String address;

	private Date createdAt;

	private Date updatedAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "MovieTheater [id=" + id + ", name=" + name + "]";
	}
}