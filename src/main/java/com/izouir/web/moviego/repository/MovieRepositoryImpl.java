package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class MovieRepositoryImpl implements MovieRepository {
    private final EntityManager ENTITY_MANAGER;

    public MovieRepositoryImpl(@Autowired EntityManager entityManager) {
        this.ENTITY_MANAGER = entityManager;
    }

    @Override
    public Optional<Movie> findById(Long id) {
        Query query = ENTITY_MANAGER.createNativeQuery("SELECT * FROM MOVIES WHERE ID = " + id, Movie.class);
        return Optional.ofNullable((Movie) query.getSingleResult());
    }

    @Override
    public Optional<List<Movie>> findByTitleLikeIgnoreCaseOrderByRatingDesc(String title) {
        Query query = ENTITY_MANAGER.createNativeQuery("SELECT * FROM MOVIES " +
                "WHERE UPPER(TITLE) LIKE UPPER('%" + title + "%') ORDER BY RATING DESC", Movie.class);
        return Optional.ofNullable((List<Movie>) query.getResultList());
    }

    @Override
    public void updateByIdIncrementViews(Long id) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET VIEWS = (VIEWS + 1) WHERE ID = " + id);
        query.executeUpdate();
    }

    @Override
    public void updateByIdDecrementViews(Long id) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET VIEWS = (VIEWS - 1) WHERE ID = " + id);
        query.executeUpdate();
    }

    @Override
    public void updateByIdSetRating(Long id, Double rating) {
        Query query = ENTITY_MANAGER.createNativeQuery("UPDATE MOVIES SET RATING = " + rating + " WHERE ID = " + id);
        query.executeUpdate();
    }
}
