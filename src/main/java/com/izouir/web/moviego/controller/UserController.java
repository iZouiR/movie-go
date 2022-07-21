package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final MovieService MOVIE_SERVICE;

    private String currentMovieName = "";

    public UserController(@Autowired MovieService MOVIE_SERVICE) {
        this.MOVIE_SERVICE = MOVIE_SERVICE;
    }

    @GetMapping("/")
    public String openHomePage(Model model) {
        model.addAttribute("movieName", currentMovieName);
        model.addAttribute("movies", MOVIE_SERVICE.searchForMovies(currentMovieName));
        return "home";
    }

    @PostMapping("/searchForMovies")
    public String searchForMovies(@ModelAttribute("movieName") String movieName) {
        currentMovieName = movieName;
        return "redirect:/";
    }

}
