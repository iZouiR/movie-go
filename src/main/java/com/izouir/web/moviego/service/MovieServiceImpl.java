package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.exception.MovieNotFoundException;
import com.izouir.web.moviego.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(final MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie findMovie(final Long id) throws MovieNotFoundException {
        final Optional<Movie> foundMovie = movieRepository.findById(id);
        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(String.format("Movie with id=%d was not found", id));
        }
        return foundMovie.get();
    }

    @Override
    public List<Movie> findMovies(final String searchContent) throws MovieNotFoundException {
        final Optional<List<Movie>> foundMovies = movieRepository.findByTitleContainingIgnoreCaseOrderByRatingDesc(searchContent);
        if (foundMovies.isEmpty()) {
            throw new MovieNotFoundException(String.format("No movies were found using searchContent=%s", searchContent));
        }
        return foundMovies.get();
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
    public void updateMovieUpdateRating(final Long id) throws MovieNotFoundException {
        final Optional<Movie> foundMovie = movieRepository.findById(id);
        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(String.format("Movie with id=%d was not found, rating can not be updated", id));
        }
        double rating = 0.0d;
        final Set<Rate> rates = foundMovie.get().getRates();
        if (!rates.isEmpty()) {
            for (final Rate rate : rates) {
                rating += rate.getPoints();
            }
            rating /= rates.size();
        }
        movieRepository.updateByIdSetRating(id, rating);
    }
}
