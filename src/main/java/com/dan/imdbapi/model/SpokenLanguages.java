package com.dan.imdbapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * SpokenLanguages
 */
@Document
@JsonRootName("spoken_languages")
public class SpokenLanguages {

	@JsonProperty("iso_639_1")
	public String iso_639_1;

	@JsonProperty("name")
	public String name;

	@Override
	public String toString() {
		return "SpokenLanguages [iso_639_1=" + iso_639_1 + ", name=" + name + "]";
	}

}