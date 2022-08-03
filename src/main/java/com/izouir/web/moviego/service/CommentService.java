package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Comment;
import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public void addComment(final String content, final User user, final Movie movie) {
        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setTime(new Timestamp(new Date().getTime()));
        comment.setUser(user);
        comment.setMovie(movie);
        commentRepository.save(comment);
    }

    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }
}
