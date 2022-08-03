package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCaseOrderByRatingDesc(String title);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Movie m set m.views = (m.views + 1) where m.movieId = :id")
    void updateByIdIncrementViews(@Param("id") Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Movie m set m.views = (m.views - 1) where m.movieId = :id")
    void updateByIdDecrementViews(@Param("id") Long id);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("update Movie m set m.rating = :rating where m.movieId = :id")
    void updateByIdSetRating(@Param("id") Long id, @Param("rating") Double rating);
}
