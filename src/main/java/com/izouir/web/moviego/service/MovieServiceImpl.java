package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository MOVIE_REPOSITORY;

    private Double calculateMovieRate(Long movieId) {
        double movieRate = 0;
        List<Rate> rates = MOVIE_REPOSITORY.findById(movieId).getRates();
        if (!rates.isEmpty()) {
            for (Rate forRate : rates) {
                movieRate += forRate.getRate();
            }
            movieRate /= rates.size();
        }
        return movieRate;
    }

    public MovieServiceImpl(@Autowired MovieRepository movieRepository) {
        this.MOVIE_REPOSITORY = movieRepository;
    }

    @Override
    @Transactional
    public Movie getMovie(Long movieId) {
        return MOVIE_REPOSITORY.findById(movieId);
    }

    @Override
    @Transactional
    public void incrementViewsForMovie(Long movieId) {
        MOVIE_REPOSITORY.incrementViewsForMovie(movieId);
    }

    @Override
    @Transactional
    public void decrementViewsForMovie(Long movieId) {
        MOVIE_REPOSITORY.decrementViewsForMovie(movieId);
    }

    @Override
    @Transactional
    public List<Movie> getMoviesLike(String movieName) {
        return MOVIE_REPOSITORY.findByMovieNameLikeIgnoreCaseOrderByRate(movieName);
    }

    @Override
    @Transactional
    public void doRateMovie(Long movieId, Long userId, Double rate) {
        MOVIE_REPOSITORY.doRateMovie(movieId, userId, rate);
        MOVIE_REPOSITORY.setMovieRate(movieId, calculateMovieRate(movieId));
    }

    @Override
    @Transactional
    public void undoRateMovie(Long movieId, Long userId) {
        MOVIE_REPOSITORY.undoRateMovie(movieId, userId);
        MOVIE_REPOSITORY.setMovieRate(movieId, calculateMovieRate(movieId));
    }

    @Override
    @Transactional
    public void updateMovieAddComment(Long movieId, Long userId, String comment) {
        MOVIE_REPOSITORY.addMovieComment(movieId, userId, comment);
    }

    @Override
    @Transactional
    public void updateMovieDeleteComment(Long commentId) {
        MOVIE_REPOSITORY.deleteMovieComment(commentId);
    }
}
