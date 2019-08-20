package com.dan.imdbapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class ImdbapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImdbapiApplication.class, args);
	}

}
