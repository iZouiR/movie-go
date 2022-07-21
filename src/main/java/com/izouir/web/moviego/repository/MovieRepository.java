package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;

public interface MovieRepository {

    List<Movie> findByMovieNameLikeIgnoreCaseOrderByRate(String movieName);

}
