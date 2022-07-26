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
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "director")
    private String director;

    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private int year;

    @Column(name = "views")
    private int views;

    @Column(name = "rating")
    private double rating;

    @OneToMany
    @JoinColumn(name = "movie_id")
    private List<Rate> rates;

    @OneToMany
    @JoinColumn(name = "movie_id")
    private List<Comment> comments;

    public Movie() {
        this.rates = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Movie(String title, String director, String description, int year, int views, double rating) {
        this.title = title;
        this.director = director;
        this.description = description;
        this.year = year;
        this.views = views;
        this.rating = rating;
        this.rates = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
