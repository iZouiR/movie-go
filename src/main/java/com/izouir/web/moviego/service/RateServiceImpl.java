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
    private final RateRepository rateRepository;

    @Autowired
    public RateServiceImpl(final RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Rate findRate(final Long movieId, final Long userId) throws RateNotFoundException {
        final Optional<Rate> foundRate = rateRepository.findByUserIdAndMovieId(userId, movieId);
        if (foundRate.isEmpty()) {
            throw new RateNotFoundException(String.format("Rate with movieId=%d and userId=%d was not found", movieId, userId));
        }
        return foundRate.get();
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
