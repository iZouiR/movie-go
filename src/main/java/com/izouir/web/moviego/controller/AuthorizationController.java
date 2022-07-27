package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.AuthorityNotFoundException;
import com.izouir.web.moviego.exception.UserNotFoundException;
import com.izouir.web.moviego.service.AuthorityService;
import com.izouir.web.moviego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthorizationController(final AuthorityService authorityService,
                                   final UserService userService,
                                   final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityService = authorityService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
                                        @ModelAttribute("password") String password) {
        final ModelAndView modelAndView = new ModelAndView("redirect:/login");

        User existingUser = null;
        try {
            existingUser = userService.findUser(username);
        } catch (final UserNotFoundException ignored) {
        }

        if (existingUser != null) {
            modelAndView.addObject("message", "a user with entered username is already exists");
            modelAndView.setViewName("register");
        } else {
            try {
                final Set<Authority> authorities = Collections.singleton(authorityService.findAuthority("ROLE_USER"));
                password = bCryptPasswordEncoder.encode(password);
                userService.saveUser(username, password, true, authorities);
            } catch (final AuthorityNotFoundException exception) {
                exception.printStackTrace();
            }
        }
        return modelAndView;
    }
}
