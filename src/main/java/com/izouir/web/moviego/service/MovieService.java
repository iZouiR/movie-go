package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieService {
    Movie findMovie(Long id);

    List<Movie> findMovies(String searchContent);

    void updateMovieIncrementViews(Long id);

    void updateMovieDecrementViews(Long id);

    void updateMovieUpdateRating(Long id);
}
