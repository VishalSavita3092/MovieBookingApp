package com.upgrad.MovieApp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MovieAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	/**
	 * insert into Movie("movieId", "movieName", "movieDescription", "releaseDate", "duration", "coverPhotoUrl", "trailerUrl")
	 * values(1, "Titanic", "This movie is based on Titanic Ship", "2005-12-31", 180, "Photo Url", "Trailer Url");
	 */

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
}
