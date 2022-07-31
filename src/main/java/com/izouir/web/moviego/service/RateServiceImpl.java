package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;

    @Autowired
    public RateServiceImpl(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Rate findRateIfExists(final Long movieId, final Long userId) {
        return rateRepository.findByUserIdAndMovieId(userId, movieId)
                .orElse(new Rate());
    }

    @Override
    @Transactional
    public void addRate(final User user, final Movie movie, final Integer points) {
        final Rate rate = new Rate();
        rate.setUser(user);
        rate.setMovie(movie);
        rate.setPoints(points);
        rateRepository.save(rate);
    }

    @Override
    @Transactional
    public void deleteRate(final Long id) {
        rateRepository.deleteById(id);
    }
}
