package com.upgrad.MovieApp.services;

import com.upgrad.MovieApp.dao.MovieDao;
import com.upgrad.MovieApp.entities.Movie;
import com.upgrad.MovieApp.entities.Theatre;
import com.upgrad.MovieApp.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService{
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Movie acceptMovie(Movie movie) {
        return movieDao.save(movie);
    }

    @Override
    public List<Movie> acceptMultipleMovies(List<Movie> movies) {
        List<Movie> savedMovie = new ArrayList<>();
        for(Movie movie : movies){
            savedMovie.add(acceptMovie(movie));
        }
        return savedMovie;
    }

    @Override
    public Movie getMovieDetail(int id) {
        return movieDao.findById(id).get();
    }

    @Override
    public Movie updateMovieDetails(int id, Movie movie) {
        Movie savedMovie = getMovieDetail(id);
        savedMovie.setDuration(movie.getDuration());
        savedMovie.setMovieDescription(movie.getMovieDescription());
        savedMovie.setMovieName(movie.getMovieName());
        savedMovie.setReleaseDate(movie.getReleaseDate());
        savedMovie.setCoverPhotoUrl(movie.getCoverPhotoUrl());
        movieDao.save(savedMovie);
        return savedMovie;
    }

    @Override
    public boolean deleteMovie(int id) {
        Movie savedMovies = getMovieDetail(id);
        if(savedMovies == null){
            return false;
        }
        movieDao.delete(savedMovies);
        return true;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDao.findAll();
    }

    @Override
    public Page<Movie> getPaginatedMovieDetails(Pageable pageable) {
        return movieDao.findAll(pageable);
    }

    @Override
    public Boolean bookMovie(User user, Movie movie, Theatre theatre){

//        Check Whether requested movie valid or not
        Optional<Movie> requestedMovie = movieDao.findById(movie.getMovieId());
        if(!requestedMovie.isPresent()){
            return false;
        }
//        Whether the use is valid or not
        Map<String, String> userUriMap = new HashMap<>();
        userUriMap.put("id", String.valueOf(user.getUserId()));
        String userAppUrl = "http://localhost:8085/user_app/v1/users/{id}";
        User receivedUser = restTemplate.getForObject(userAppUrl, User.class, userUriMap);
        if(receivedUser == null){
            return false;
        }
//        Whether theatre and movie combination is available or not
        Map<String, String> theatreUriMap = new HashMap<>();
        theatreUriMap.put("theaterId ", String.valueOf(theatre.getTheatreId()));
        theatreUriMap.put("movieId", String.valueOf(theatre.getMovieId()));
        String theaterAppUrl = "http://localhost:8083/theatre_app/v1/theatres/{theatreId}/movie/{movieId}";
        Theatre receivedTheatre = restTemplate.getForObject(theaterAppUrl, Theatre.class, theatreUriMap);
        if (receivedTheatre == null) {
            return false;
        }


        return true;
    }
}
