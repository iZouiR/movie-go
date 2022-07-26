package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.exception.MovieNotFoundException;

import java.util.List;

public interface MovieService {
    Movie findMovie(Long id) throws MovieNotFoundException;

    List<Movie> findMovies(String searchContent) throws MovieNotFoundException;

    void updateMovieIncrementViews(Long id);

    void updateMovieDecrementViews(Long id);

    void updateMovieUpdateRating(Long id) throws MovieNotFoundException;
}
