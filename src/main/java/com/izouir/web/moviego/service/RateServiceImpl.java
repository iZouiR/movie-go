package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.RateNotFoundException;
import com.izouir.web.moviego.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository RATE_REPOSITORY;

    public RateServiceImpl(@Autowired RateRepository RATE_REPOSITORY) {
        this.RATE_REPOSITORY = RATE_REPOSITORY;
    }

    @Override
    @Transactional
    public Rate findRate(Long movieId, Long userId) throws RateNotFoundException {
        Optional<Rate> foundRate = RATE_REPOSITORY.findByUserIdAndMovieId(userId, movieId);
        if (foundRate.isEmpty()) {
            throw new RateNotFoundException(String.format("Rate with movieId=%d and userId=%d was not found", movieId, userId));
        }
        return foundRate.get();
    }

    @Override
    @Transactional
    public void addRate(User user, Movie movie, Integer points) {
        Rate rate = new Rate();
        rate.setUser(user);
        rate.setMovie(movie);
        rate.setPoints(points);
        RATE_REPOSITORY.save(rate);
    }

    @Override
    @Transactional
    public void deleteRate(Long id) {
        RATE_REPOSITORY.deleteById(id);
    }
}
