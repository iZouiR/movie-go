package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RateService {
    private final RateRepository rateRepository;

    @Autowired
    public RateService(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Rate findRateIfExists(final User user, final Movie movie) {
        return rateRepository.findByUserAndMovie(user, movie)
                .orElse(new Rate());
    }

    public void addRate(final User user, final Movie movie, final Integer points) {
        final Rate rate = new Rate();
        rate.setUser(user);
        rate.setMovie(movie);
        rate.setPoints(points);
        rateRepository.save(rate);
    }

    public void deleteRate(final Long id) {
        rateRepository.deleteById(id);
    }
}
