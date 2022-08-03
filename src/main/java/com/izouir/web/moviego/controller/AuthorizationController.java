package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.service.AuthorityService;
import com.izouir.web.moviego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Set;

@Controller
public class AuthorizationController {
    private final AuthorityService authorityService;
    private final UserService userService;

    @Autowired
    public AuthorizationController(final AuthorityService authorityService,
                                   final UserService userService) {
        this.authorityService = authorityService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView openLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView openRegisterPage() {
        return new ModelAndView("register");
    }

    @PostMapping("/register")
    public ModelAndView registerNewUser(@ModelAttribute("username") final String username,
                                        @ModelAttribute("password") final String password) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/login");
        if (userService.userExists(username)) {
            modelAndView.addObject("message", "a user with entered username is already exists");
            modelAndView.setViewName("register");
        } else {
            final Set<Authority> authorities = Collections.singleton(authorityService.findAuthority(1L));
            userService.saveUser(username, password, true, authorities);
        }
        return modelAndView;
    }
}
