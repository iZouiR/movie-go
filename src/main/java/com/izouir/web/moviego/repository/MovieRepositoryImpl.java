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

    private final EntityManager ENTITY_MANAGER;

    public MovieRepositoryImpl(@Autowired EntityManager entityManager) {
        this.ENTITY_MANAGER = entityManager;
    }

    @Override
    public Movie findByMovieName(String movieName) {
        Query query = ENTITY_MANAGER.createNativeQuery("SELECT * FROM MOVIES WHERE MOVIE_NAME = '"
                + movieName + "'", Movie.class);
        return (Movie) query.getResultList().get(0);
    }

    @Override
    public List<Movie> findByMovieNameLikeIgnoreCaseOrderByRate(String movieName) {
        Query query = ENTITY_MANAGER.createNativeQuery("SELECT * FROM MOVIES WHERE UPPER(MOVIE_NAME) LIKE '%"
                + movieName.toUpperCase(Locale.ROOT) + "%' ORDER BY RATE", Movie.class);
        return (List<Movie>) query.getResultList();
    }

    @Override
    public void incrementViewsForMovie(String movieName) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET VIEWS = (VIEWS+1) WHERE MOVIE_NAME = '"
                + movieName + "'");
        query.executeUpdate();
    }
}
