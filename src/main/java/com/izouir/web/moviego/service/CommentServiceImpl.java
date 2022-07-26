package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Comment;
import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository COMMENT_REPOSITORY;

    public CommentServiceImpl(@Autowired CommentRepository COMMENT_REPOSITORY) {
        this.COMMENT_REPOSITORY = COMMENT_REPOSITORY;
    }

    @Override
    @Transactional
    public void addComment(String content, User user, Movie movie) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setMovie(movie);
        COMMENT_REPOSITORY.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        COMMENT_REPOSITORY.deleteById(id);
    }
}
