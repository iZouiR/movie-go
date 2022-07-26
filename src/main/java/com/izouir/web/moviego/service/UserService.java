package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    User findUser(String username) throws UserNotFoundException;

    void saveUser(String username, String password, Boolean enabled, List<Authority> authorities);
}
