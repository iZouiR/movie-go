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

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository MOVIE_REPOSITORY;

    public MovieServiceImpl(@Autowired MovieRepository MOVIE_REPOSITORY) {
        this.MOVIE_REPOSITORY = MOVIE_REPOSITORY;
    }

    @Override
    @Transactional
    public Movie findMovie(Long id) throws MovieNotFoundException {
        Optional<Movie> foundMovie = MOVIE_REPOSITORY.findById(id);
        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(String.format("Movie with id=%d was not found", id));
        }
        return foundMovie.get();
    }

    @Override
    @Transactional
    public List<Movie> findMovies(String searchContent) throws MovieNotFoundException {
        Optional<List<Movie>> foundMovies = MOVIE_REPOSITORY.findByTitleContainingIgnoreCaseOrderByRatingDesc(searchContent);
        if (foundMovies.isEmpty()) {
            throw new MovieNotFoundException(String.format("No movies were found using searchContent=%s", searchContent));
        }
        return foundMovies.get();
    }

    @Override
    @Transactional
    public void updateMovieIncrementViews(Long id) {
        MOVIE_REPOSITORY.updateByIdIncrementViews(id);
    }

    @Override
    @Transactional
    public void updateMovieDecrementViews(Long id) {
        MOVIE_REPOSITORY.updateByIdDecrementViews(id);
    }

    @Override
    @Transactional
    public void updateMovieUpdateRating(Long id) throws MovieNotFoundException {
        Optional<Movie> foundMovie = MOVIE_REPOSITORY.findById(id);
        if (foundMovie.isEmpty()) {
            throw new MovieNotFoundException(String.format("Movie with id=%d was not found, rating can not be updated", id));
        }
        double rating = 0.0d;
        List<Rate> rates = foundMovie.get().getRates();
        if (!rates.isEmpty()) {
            for (Rate rate : rates) {
                rating += rate.getPoints();
            }
            rating /= rates.size();
        }
        MOVIE_REPOSITORY.updateByIdSetRating(id, rating);
    }
}
