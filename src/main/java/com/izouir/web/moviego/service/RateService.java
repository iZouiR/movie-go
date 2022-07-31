package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;

public interface RateService {
    Rate findRateIfExists(Long movieId, Long userId);

    void addRate(User user, Movie movie, Integer points);

    void deleteRate(Long id);
}
