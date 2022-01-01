package com.upgrad.MovieApp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity

@Getter
@Setter
@ToString
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int movieId;

    @Column(length = 50, nullable = false,  unique = true)
    private String movieName;

    @Column(name="movie_desc", nullable = false, length = 500)
    private String movieDescription;

    @Column(nullable = false)
    private LocalDateTime releaseDate;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false, length = 500)
    private String coverPhotoUrl;

    @Column(nullable = false, length = 500)
    private String trailerUrl;

}
