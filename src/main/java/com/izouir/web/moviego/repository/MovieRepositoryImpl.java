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
    public Movie findById(Long movieId) {
        Query query = ENTITY_MANAGER.createNativeQuery("SELECT * FROM MOVIES WHERE ID = " + movieId, Movie.class);
        return (Movie) query.getResultList().get(0);
    }

    @Override
    public void incrementViewsForMovie(Long movieId) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET VIEWS = (VIEWS+1) WHERE ID = " + movieId);
        query.executeUpdate();
    }

    @Override
    public void decrementViewsForMovie(Long movieId) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET VIEWS = (VIEWS-1) WHERE ID = " + movieId);
        query.executeUpdate();
    }

    @Override
    public List<Movie> findByMovieNameLikeIgnoreCaseOrderByRate(String movieName) {
        Query query = ENTITY_MANAGER.createNativeQuery("SELECT * FROM MOVIES WHERE UPPER(MOVIE_NAME) LIKE '%"
                + movieName.toUpperCase(Locale.ROOT) + "%' ORDER BY RATE DESC", Movie.class);
        return (List<Movie>) query.getResultList();
    }

    @Override
    public void setMovieRate(Long movieId, Double movieRate) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET RATE = " + movieRate + " WHERE ID = " + movieId);
        query.executeUpdate();
    }

    @Override
    public void doRateMovie(Long movieId, Long userId, Double rate) {
        Query query = ENTITY_MANAGER.createNativeQuery("INSERT INTO RATES (MOVIE_ID, USER_ID, RATE) " +
                "VALUES (" + movieId + ", " + userId + ", " + rate + ")");
        query.executeUpdate();
    }

    @Override
    public void undoRateMovie(Long movieId, Long userId) {
        Query query = ENTITY_MANAGER.createNativeQuery("DELETE FROM RATES WHERE MOVIE_ID = " + movieId + " AND USER_ID = " + userId);
        query.executeUpdate();
    }
}
