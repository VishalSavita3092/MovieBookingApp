package com.upgrad.MovieApp.services;

import com.upgrad.MovieApp.entities.Movie;
import com.upgrad.MovieApp.entities.Theatre;
import com.upgrad.MovieApp.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {
    public Movie acceptMovie(Movie movie);

    public List<Movie> acceptMultipleMovies(List<Movie> movies);

    public Movie getMovieDetail(int id);

    public Movie updateMovieDetails(int id, Movie movie);

    public boolean deleteMovie(int id);

    public List<Movie> getAllMovies();

    public Page<Movie> getPaginatedMovieDetails(Pageable pageable);

    public Boolean bookMovie(User user, Movie movie, Theatre theatre);
}
