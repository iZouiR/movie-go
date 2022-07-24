package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieRepository {

    Movie findById(Long movieId);
    void incrementViewsForMovie(Long movieId);
    void decrementViewsForMovie(Long movieId);
    List<Movie> findByMovieNameLikeIgnoreCaseOrderByRate(String movieName);
    void setMovieRate(Long movieId, Double movieRate);
    void doRateMovie(Long movieId, Long userId, Double rate);
    void undoRateMovie(Long movieId, Long userId);

}
