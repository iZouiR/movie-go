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
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(final CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    @Transactional
    public void addComment(final String content, final User user, final Movie movie) {
        final Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setMovie(movie);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long id) {
        commentRepository.deleteById(id);
    }
}
