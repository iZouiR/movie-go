package com.izouir.web.moviego.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_id_generator")
    @SequenceGenerator(name = "comment_id_generator", sequenceName = "comment_id_generator", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "content")
    private String content;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    public Comment() {
    }

    public Comment(final String content, final User user, final Movie movie) {
        this.content = content;
        this.user = user;
        this.movie = movie;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(final Movie movie) {
        this.movie = movie;
    }
}
