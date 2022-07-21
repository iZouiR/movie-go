package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository MOVIE_REPOSITORY;

    public MovieServiceImpl(@Autowired MovieRepository movieRepository) {
        this.MOVIE_REPOSITORY = movieRepository;
    }

    @Override
    @Transactional
    public Movie getMovie(String movieName) {
        return MOVIE_REPOSITORY.findByMovieName(movieName);
    }

    @Override
    @Transactional
    public List<Movie> getMoviesLike(String movieName) {
        return MOVIE_REPOSITORY.findByMovieNameLikeIgnoreCaseOrderByRate(movieName);
    }

    @Override
    @Transactional
    public void incrementViewsForMovie(String movieName) {
        MOVIE_REPOSITORY.incrementViewsForMovie(movieName);
    }
}
