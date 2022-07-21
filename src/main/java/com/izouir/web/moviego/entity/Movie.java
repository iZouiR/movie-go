package com.izouir.web.moviego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_generator")
    @SequenceGenerator(name = "movie_id_generator", sequenceName = "movie_id_generator", allocationSize = 1)
    @Column(name = "id")
    private int id;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "author")
    private String author;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private int year;

    @Column(name = "views")
    private int views;

    @Column(name = "rate")
    private double rate;

    @Column(name = "rated")
    private int rated;

    @OneToMany
    @JoinColumn(name = "movie_id")
    private List<Comment> comments;

    public Movie() {
        this.comments = new ArrayList<>();
    }

    public Movie(String movieName, String author, String description, int year, int views, double rate, int rated) {
        this.movieName = movieName;
        this.author = author;
        this.description = description;
        this.year = year;
        this.views = views;
        this.rate = rate;
        this.rated = rated;
        this.comments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getRated() {
        return rated;
    }

    public void setRated(int rated) {
        this.rated = rated;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}