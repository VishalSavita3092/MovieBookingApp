package com.upgrad.MovieApp.dao;

import com.upgrad.MovieApp.entities.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDao extends JpaRepository<Movie, Integer> {
}
