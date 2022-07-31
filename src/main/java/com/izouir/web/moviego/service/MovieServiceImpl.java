package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.exception.MovieNotFoundException;
import com.izouir.web.moviego.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie findMovie(final Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie with id=" + id + " was not found"));
    }

    @Override
    public List<Movie> findMovies(final String searchContent) {
        return movieRepository.findByTitleContainingIgnoreCaseOrderByRatingDesc(searchContent)
                .orElse(Collections.emptyList());
    }

    @Override
    @Transactional
    public void updateMovieIncrementViews(final Long id) {
        movieRepository.updateByIdIncrementViews(id);
    }

    @Override
    @Transactional
    public void updateMovieDecrementViews(final Long id) {
        movieRepository.updateByIdDecrementViews(id);
    }

    @Override
    @Transactional
    public void updateMovieUpdateRating(final Long id) {
        final Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie with id=" + id + " was not found, rating can not be updated"));
        double rating = 0.0d;
        final Set<Rate> rates = foundMovie.getRates();
        if (!rates.isEmpty()) {
            for (final Rate rate : rates) {
                rating += rate.getPoints();
            }
            rating /= rates.size();
        }
        movieRepository.updateByIdSetRating(id, rating);
    }
}
