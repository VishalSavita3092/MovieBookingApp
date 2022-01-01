package com.upgrad.MovieApp.controller;

import com.upgrad.MovieApp.dto.MovieBookingDTO;
import com.upgrad.MovieApp.dto.MovieDTO;
import com.upgrad.MovieApp.entities.Movie;
import com.upgrad.MovieApp.entities.Theatre;
import com.upgrad.MovieApp.entities.User;
import com.upgrad.MovieApp.services.MovieService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.Mergeable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/movie_app/v1")
public class MovieController {
    @Autowired
    private MovieService movieService;


    @Autowired
    ModelMapper modelMapper;

    /**
     * Method for creating movies
     * 127.0.0.1:8080/movie_app/v1/movies
     */

    @PostMapping(value = "/movies", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createMovie(@RequestBody MovieDTO movieDTO){
        /**
         * Convert MovieDTO to movieEntity
         */
        Movie newMovie = modelMapper.map(movieDTO, Movie.class);
        Movie savedMovie = movieService.acceptMovie(newMovie);
        MovieDTO savedMovieDTO = modelMapper.map(savedMovie, MovieDTO.class);
        return new ResponseEntity(savedMovieDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllMovies(){
        List<Movie> movieList = movieService.getAllMovies();
        List<MovieDTO> movieDTOList = new ArrayList<>();

        for (Movie movie : movieList){
            movieDTOList.add(modelMapper.map(movie, MovieDTO.class));
        }
        return new ResponseEntity(movieDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "movie/{id}")
    public ResponseEntity getMovieBasedOnID(@PathVariable(name = "id") int id){
        Movie responseMovie = movieService.getMovieDetail(id);

        MovieDTO responseMovieDTO = modelMapper.map(responseMovie, MovieDTO.class);
        return new ResponseEntity(responseMovieDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/movies/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateMovieDetails(@PathVariable(name = "id")  int id, @RequestBody MovieDTO movieDTO){
        Movie newMovie = modelMapper.map(movieDTO, Movie.class);
        Movie updatedMovie = movieService.updateMovieDetails(id, newMovie);

        MovieDTO updatedMovieDTO = modelMapper.map(updatedMovie, MovieDTO.class);
        return new ResponseEntity(updatedMovieDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/bookings/movie")
    public ResponseEntity bookMovieDetails(@RequestBody MovieBookingDTO movieBookingDTO){
        Movie requestedMovie = modelMapper.map(movieBookingDTO.getMovie(), Movie.class);
        User fromUser = modelMapper.map(movieBookingDTO.getUser(), User.class);
        Theatre theatre = modelMapper.map(movieBookingDTO.getTheatre(), Theatre.class);

        boolean isValidBooking = movieService.bookMovie(fromUser, requestedMovie, theatre);
        if(!isValidBooking){
            return new ResponseEntity("Not Booked", HttpStatus.OK);
        }
        return new ResponseEntity("Booked Successfully", HttpStatus.OK);

    }

}
