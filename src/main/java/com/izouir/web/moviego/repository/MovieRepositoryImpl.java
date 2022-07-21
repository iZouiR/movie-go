package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Locale;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    private final EntityManager entityManager;

    public MovieRepositoryImpl(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Movie> findByMovieNameLikeIgnoreCaseOrderByRate(String movieName) {
        Query query = entityManager.createNativeQuery("SELECT * FROM MOVIES WHERE UPPER(MOVIE_NAME) LIKE '%"
                + movieName.toUpperCase(Locale.ROOT) + "%' ORDER BY RATE", Movie.class);
        return (List<Movie>) query.getResultList();
    }
}
