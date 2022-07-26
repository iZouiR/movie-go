package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.MovieNotFoundException;
import com.izouir.web.moviego.exception.RateNotFoundException;
import com.izouir.web.moviego.exception.UserNotFoundException;
import com.izouir.web.moviego.service.CommentService;
import com.izouir.web.moviego.service.MovieService;
import com.izouir.web.moviego.service.RateService;
import com.izouir.web.moviego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private final CommentService COMMENT_SERVICE;
    private final MovieService MOVIE_SERVICE;
    private final RateService RATE_SERVICE;
    private final UserService USER_SERVICE;

    public MovieController(@Autowired CommentService COMMENT_SERVICE,
                           @Autowired MovieService MOVIE_SERVICE,
                           @Autowired RateService RATE_SERVICE,
                           @Autowired UserService USER_SERVICE) {
        this.COMMENT_SERVICE = COMMENT_SERVICE;
        this.MOVIE_SERVICE = MOVIE_SERVICE;
        this.RATE_SERVICE = RATE_SERVICE;
        this.USER_SERVICE = USER_SERVICE;
    }

    @GetMapping("/")
    public ModelAndView openMovieIndexPage(@RequestParam(name = "searchContent", defaultValue = "") String searchContent) {
        ModelAndView modelAndView = new ModelAndView("movie/index");
        List<Movie> foundMovies = new ArrayList<>();
        try {
            foundMovies = MOVIE_SERVICE.findMovies(searchContent);
        } catch (MovieNotFoundException ignored) {
        }
        modelAndView.addObject("foundMovies", foundMovies);
        return modelAndView;
    }

    @GetMapping("/{movieId}")
    public ModelAndView openMoviePage(@PathVariable("movieId") Long movieId,
                                      HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("movie/movie");
        try {
            MOVIE_SERVICE.updateMovieIncrementViews(movieId);
            Movie movie = MOVIE_SERVICE.findMovie(movieId);
            modelAndView.addObject("movie", movie);

            Rate rate = new Rate();
            try {
                User user = USER_SERVICE.findUser(httpServletRequest.getRemoteUser());
                rate = RATE_SERVICE.findRate(movieId, user.getId());
            } catch (RateNotFoundException | UserNotFoundException ignored) {
            }
            modelAndView.addObject("rate", rate);
        } catch (MovieNotFoundException exception) {
            exception.printStackTrace();
        }
        return modelAndView;
    }

    @PostMapping("/addRate")
    public ModelAndView addRate(@RequestParam("movieId") Long movieId,
                                @ModelAttribute("ratePoints") Integer ratePoints,
                                HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        try {
            User user = USER_SERVICE.findUser(httpServletRequest.getRemoteUser());
            Movie movie = MOVIE_SERVICE.findMovie(movieId);
            RATE_SERVICE.addRate(user, movie, ratePoints);
            MOVIE_SERVICE.updateMovieUpdateRating(movieId);
            MOVIE_SERVICE.updateMovieDecrementViews(movieId);
        } catch (UserNotFoundException | MovieNotFoundException exception) {
            exception.printStackTrace();
        }
        return modelAndView;
    }

    @PostMapping("/deleteRate")
    public ModelAndView deleteRate(@ModelAttribute("movieId") Long movieId,
                                   @ModelAttribute("rateId") Long rateId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        try {
            RATE_SERVICE.deleteRate(rateId);
            MOVIE_SERVICE.updateMovieUpdateRating(movieId);
            MOVIE_SERVICE.updateMovieDecrementViews(movieId);
        } catch (MovieNotFoundException exception) {
            exception.printStackTrace();
        }
        return modelAndView;
    }

    @PostMapping("/addComment")
    public ModelAndView addComment(@ModelAttribute("movieId") Long movieId,
                                   @ModelAttribute("commentContent") String commentContent,
                                   HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        try {
            User user = USER_SERVICE.findUser(httpServletRequest.getRemoteUser());
            Movie movie = MOVIE_SERVICE.findMovie(movieId);
            COMMENT_SERVICE.addComment(commentContent, user, movie);
            MOVIE_SERVICE.updateMovieDecrementViews(movieId);
        } catch (UserNotFoundException | MovieNotFoundException exception) {
            exception.printStackTrace();
        }
        return modelAndView;
    }

    @PostMapping("/deleteComment")
    public ModelAndView deleteComment(@ModelAttribute("movieId") Long movieId,
                                      @ModelAttribute("commentId") Long commentId) {
        ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        COMMENT_SERVICE.deleteComment(commentId);
        MOVIE_SERVICE.updateMovieDecrementViews(movieId);
        return modelAndView;
    }
}
