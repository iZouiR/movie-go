package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieRepository {

    Movie findByMovieName(String movieName);
    List<Movie> findByMovieNameLikeIgnoreCaseOrderByRate(String movieName);
    void incrementViewsForMovie(String movieName);

}
