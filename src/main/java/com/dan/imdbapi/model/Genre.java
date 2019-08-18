package com.dan.imdbapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Genre
 */
@Document
@JsonRootName("genre")
public class Genre {

	@JsonProperty("id")
	public String id;

	@JsonProperty("name")
	public String name;

	@Override
	public String toString() {
		return "Genre [id=" + id + ", name=" + name + "]";
	}

	
}