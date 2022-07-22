package com.izouir.web.moviego.controller;

import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.service.AuthorityService;
import com.izouir.web.moviego.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class AuthorizationController {

    final UserService USER_SERVICE;
    final AuthorityService AUTHORITY_SERVICE;
    final PasswordEncoder PASSWORD_ENCODER;

    public AuthorizationController(@Autowired UserService USER_SERVICE,
                                   @Autowired AuthorityService AUTHORITY_SERVICE,
                                   @Autowired PasswordEncoder PASSWORD_ENCODER) {
        this.USER_SERVICE = USER_SERVICE;
        this.AUTHORITY_SERVICE = AUTHORITY_SERVICE;
        this.PASSWORD_ENCODER = PASSWORD_ENCODER;
    }

    @GetMapping("/login")
    public String openLoginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/register")
    public String openRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@ModelAttribute("user") User user, Model model) {
        User userFromDatabase = USER_SERVICE.getUser(user.getUsername());
        if (userFromDatabase != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            return "register";
        }
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEnabled(true);
        user.setAuthorities(Collections.singletonList(AUTHORITY_SERVICE.getAuthority("ROLE_USER")));
        USER_SERVICE.saveUser(user);
        return "redirect:/login";
    }

}
