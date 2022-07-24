package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.service.MovieService;
import com.izouir.web.moviego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MovieController {

    private final MovieService MOVIE_SERVICE;
    private final UserService USER_SERVICE;

    private String currentMovieName = "";

    public MovieController(@Autowired MovieService MOVIE_SERVICE,
                           @Autowired UserService USER_SERVICE) {
        this.MOVIE_SERVICE = MOVIE_SERVICE;
        this.USER_SERVICE = USER_SERVICE;
    }

    @GetMapping("/")
    public String openHomePage(Model model) {
        model.addAttribute("movieName", currentMovieName);
        model.addAttribute("movies", MOVIE_SERVICE.getMoviesLike(currentMovieName));
        return "home";
    }

    @PostMapping("/search")
    public String searchForMovies(@ModelAttribute("movieName") String movieName) {
        currentMovieName = movieName;
        return "redirect:/";
    }

    @GetMapping("/{movieId}")
    public String openMoviePage(@PathVariable("movieId") String movieIdAsString, Model model) {
        Long movieIdAsLong = Long.parseLong(movieIdAsString);
        MOVIE_SERVICE.incrementViewsForMovie(movieIdAsLong);

        Movie movie = MOVIE_SERVICE.getMovie(movieIdAsLong);
        model.addAttribute("movie", movie);
        model.addAttribute("rate", "");

        return "movie/movie";
    }

    @PostMapping("/doRate")
    public String doRate(@ModelAttribute("movieId") String movieIdAsString, @ModelAttribute("username") String username,
                               @ModelAttribute("rate") String rateAsString, Model model) {
        Long movieIdAsLong = Long.parseLong(movieIdAsString);
        User user = USER_SERVICE.getUser(username);
        Long userId = user.getId();
        Double rateAsDouble = Double.parseDouble(rateAsString);

        MOVIE_SERVICE.doRateMovie(movieIdAsLong, userId, rateAsDouble);
        MOVIE_SERVICE.decrementViewsForMovie(movieIdAsLong);

        Movie movie = MOVIE_SERVICE.getMovie(movieIdAsLong);
        model.addAttribute("movie", movie);
        model.addAttribute("rate", "");

        return "redirect:/movie/" + movieIdAsString;
    }

    @PostMapping("/undoRate")
    public String undoRate(@ModelAttribute("movieId") String movieIdAsString,
                           @ModelAttribute("username") String username, Model model) {
        Long movieIdAsLong = Long.parseLong(movieIdAsString);
        User user = USER_SERVICE.getUser(username);
        Long userId = user.getId();

        MOVIE_SERVICE.undoRateMovie(movieIdAsLong, userId);
        MOVIE_SERVICE.decrementViewsForMovie(movieIdAsLong);

        Movie movie = MOVIE_SERVICE.getMovie(movieIdAsLong);
        model.addAttribute("movie", movie);
        model.addAttribute("rate", "");

        return "redirect:/movie/" + movieIdAsString;
    }
}
