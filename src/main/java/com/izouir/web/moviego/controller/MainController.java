package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private final MovieService MOVIE_SERVICE;

    private String currentMovieName = "";

    public MainController(@Autowired MovieService MOVIE_SERVICE) {
        this.MOVIE_SERVICE = MOVIE_SERVICE;
    }

    @GetMapping("/")
    public String openHomePage(Model model) {
        model.addAttribute("movieName", currentMovieName);
        model.addAttribute("movies", MOVIE_SERVICE.getMoviesLike(currentMovieName));
        return "home";
    }

    @PostMapping("/searchForMovies")
    public String searchForMovies(@ModelAttribute("movieName") String movieName) {
        currentMovieName = movieName;
        return "redirect:/";
    }

    @GetMapping("/{movieName}")
    public String openMoviePage(@PathVariable("movieName") String movieName, Model model) {
        MOVIE_SERVICE.incrementViewsForMovie(movieName);
        Movie movie = MOVIE_SERVICE.getMovie(movieName);
        model.addAttribute("movie", movie);
        return "movie";
    }
}
