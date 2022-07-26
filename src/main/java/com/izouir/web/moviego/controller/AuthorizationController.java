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
import java.util.List;

@Controller
public class AuthorizationController {
    private final AuthorityService AUTHORITY_SERVICE;
    private final UserService USER_SERVICE;
    private final BCryptPasswordEncoder BCRYPT_PASSWORD_ENCODER;

    public AuthorizationController(@Autowired AuthorityService AUTHORITY_SERVICE,
                                   @Autowired UserService USER_SERVICE,
                                   @Autowired BCryptPasswordEncoder BCRYPT_PASSWORD_ENCODER) {
        this.AUTHORITY_SERVICE = AUTHORITY_SERVICE;
        this.USER_SERVICE = USER_SERVICE;
        this.BCRYPT_PASSWORD_ENCODER = BCRYPT_PASSWORD_ENCODER;
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
    public ModelAndView registerNewUser(@ModelAttribute("username") String username,
                                        @ModelAttribute("password") String password) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login");

        User existingUser = null;
        try {
            existingUser = USER_SERVICE.findUser(username);
        } catch (UserNotFoundException ignored) {
        }

        if (existingUser != null) {
            modelAndView.addObject("message", "a user with entered username is already exists");
            modelAndView.setViewName("register");
        } else {
            try {
                List<Authority> authorities = Collections.singletonList(AUTHORITY_SERVICE.findAuthority("ROLE_USER"));
                password = BCRYPT_PASSWORD_ENCODER.encode(password);
                USER_SERVICE.saveUser(username, password, true, authorities);
            } catch (AuthorityNotFoundException exception) {
                exception.printStackTrace();
            }
        }
        return modelAndView;
    }
}
