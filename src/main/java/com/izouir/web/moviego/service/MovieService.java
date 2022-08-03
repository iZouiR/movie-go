package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.exception.MovieNotFoundException;
import com.izouir.web.moviego.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie findMovie(final Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie with id=" + id + " was not found"));
    }

    public List<Movie> findMovies(final String searchContent) {
        return movieRepository.findByTitleContainingIgnoreCaseOrderByRatingDesc(searchContent);
    }

    @Transactional
    public void updateMovieIncrementViews(final Long id) {
        movieRepository.updateByIdIncrementViews(id);
    }

    @Transactional
    public void updateMovieDecrementViews(final Long id) {
        movieRepository.updateByIdDecrementViews(id);
    }

    @Transactional
    public void updateMovieUpdateRating(final Long id) {
        final Movie foundMovie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Movie with id=" + id + " was not found, rating can not be updated"));
        final Set<Rate> rates = foundMovie.getRates();
        if (!rates.isEmpty()) {
            double rating = rates.stream().mapToDouble(Rate::getPoints).sum();
            rating /= rates.size();
            movieRepository.updateByIdSetRating(id, rating);
        }
    }
}
