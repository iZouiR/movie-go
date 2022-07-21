package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie getMovie(String movieName);
    List<Movie> getMoviesLike(String movieName);
    void incrementViewsForMovie(String movieName);

}
