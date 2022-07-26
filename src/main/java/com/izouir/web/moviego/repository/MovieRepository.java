package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
    Optional<Movie> findById(Long id);

    Optional<List<Movie>> findByTitleLikeIgnoreCaseOrderByRatingDesc(String title);

    void updateByIdIncrementViews(Long id);

    void updateByIdDecrementViews(Long id);

    void updateByIdSetRating(Long id, Double rating);
}
