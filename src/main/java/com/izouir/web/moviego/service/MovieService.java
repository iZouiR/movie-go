package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> searchForMovies(String movieName);

}
