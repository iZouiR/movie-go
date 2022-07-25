package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie getMovie(Long movieId);
    void incrementViewsForMovie(Long movieId);
    void decrementViewsForMovie(Long movieId);
    List<Movie> getMoviesLike(String movieName);
    void doRateMovie(Long movieId, Long userId, Double rate);
    void undoRateMovie(Long movieId, Long userId);
    void updateMovieAddComment(Long movieIdAsLong, Long userId, String comment);
    void updateMovieDeleteComment(Long commentIdAsLong);
}
