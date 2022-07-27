package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<List<Movie>> findByTitleContainingIgnoreCaseOrderByRatingDesc(String title);

    @Modifying
    @Query("update Movie m set m.views = (m.views + 1) where m.id = ?1")
    void updateByIdIncrementViews(Long id);

    @Modifying
    @Query("update Movie m set m.views = (m.views - 1) where m.id = ?1")
    void updateByIdDecrementViews(Long id);

    @Modifying
    @Query("update Movie m set m.rating = ?2 where m.id = ?1")
    void updateByIdSetRating(Long id, Double rating);
}
