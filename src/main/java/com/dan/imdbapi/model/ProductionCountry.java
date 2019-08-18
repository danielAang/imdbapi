package com.dan.imdbapi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@Document
@JsonRootName("production_countries")
public class ProductionCountry {

	@JsonProperty("iso_3166_1")
	public String iso_3166_1;

	@JsonProperty("name")
	public String name;

	@Override
	public String toString() {
		return "ProductionCountry [iso_3166_1=" + iso_3166_1 + ", name=" + name + "]";
	}
}