package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.User;

public interface CommentService {
    void addComment(String content, User user, Movie movie);

    void deleteComment(Long id);
}
