package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.Movie;
import com.izouir.web.moviego.entity.Rate;
import com.izouir.web.moviego.entity.User;
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
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieController {
    private final CommentService commentService;
    private final MovieService movieService;
    private final RateService rateService;
    private final UserService userService;

    @Autowired
    public MovieController(final CommentService commentService,
                           final MovieService movieService,
                           final RateService rateService,
                           final UserService userService) {
        this.commentService = commentService;
        this.movieService = movieService;
        this.rateService = rateService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ModelAndView openMovieIndexPage(@RequestParam(name = "searchContent", defaultValue = "") final String searchContent) {
        final ModelAndView modelAndView = new ModelAndView("movie/index");
        final List<Movie> foundMovies = movieService.findMovies(searchContent);
        modelAndView.addObject("foundMovies", foundMovies);
        return modelAndView;
    }

    @GetMapping("/{movieId}")
    public ModelAndView openMoviePage(@PathVariable("movieId") final Long movieId,
                                      final HttpServletRequest httpServletRequest) {
        final ModelAndView modelAndView = new ModelAndView("movie/movie");
        final Movie movie = movieService.findMovie(movieId);
        final User user = userService.findUser(httpServletRequest.getRemoteUser());
        final Rate rate = rateService.findRateIfExists(movieId, user.getId());
        movieService.updateMovieIncrementViews(movieId);
        modelAndView.addObject("movie", movie);
        modelAndView.addObject("rate", rate);
        return modelAndView;
    }

    @PostMapping("/addRate")
    public ModelAndView addRate(@RequestParam("movieId") final Long movieId,
                                @ModelAttribute("ratePoints") final Integer ratePoints,
                                final HttpServletRequest httpServletRequest) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        final Movie movie = movieService.findMovie(movieId);
        final User user = userService.findUser(httpServletRequest.getRemoteUser());
        rateService.addRate(user, movie, ratePoints);
        movieService.updateMovieUpdateRating(movieId);
        movieService.updateMovieDecrementViews(movieId);
        return modelAndView;
    }

    @PostMapping("/deleteRate")
    public ModelAndView deleteRate(@ModelAttribute("movieId") final Long movieId,
                                   @ModelAttribute("rateId") final Long rateId) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        rateService.deleteRate(rateId);
        movieService.updateMovieUpdateRating(movieId);
        movieService.updateMovieDecrementViews(movieId);
        return modelAndView;
    }

    @PostMapping("/addComment")
    public ModelAndView addComment(@ModelAttribute("movieId") final Long movieId,
                                   @ModelAttribute("commentContent") final String commentContent,
                                   final HttpServletRequest httpServletRequest) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        final Movie movie = movieService.findMovie(movieId);
        final User user = userService.findUser(httpServletRequest.getRemoteUser());
        commentService.addComment(commentContent, user, movie);
        movieService.updateMovieDecrementViews(movieId);
        return modelAndView;
    }

    @PostMapping("/deleteComment")
    public ModelAndView deleteComment(@ModelAttribute("movieId") final Long movieId,
                                      @ModelAttribute("commentId") final Long commentId) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/movie/" + movieId);
        commentService.deleteComment(commentId);
        movieService.updateMovieDecrementViews(movieId);
        return modelAndView;
    }
}
