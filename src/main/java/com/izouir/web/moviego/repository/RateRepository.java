package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByUserIdAndMovieId(Long userId, Long movieId);
}
