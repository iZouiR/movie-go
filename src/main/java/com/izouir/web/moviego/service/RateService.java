package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.RateNotFoundException;

public interface RateService {
    Rate findRate(Long movieId, Long userId) throws RateNotFoundException;

    void addRate(User user, Movie movie, Integer points);

    void deleteRate(Long id);
}
