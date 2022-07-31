package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.entity.User;

import java.util.Set;

public interface UserService {
    boolean exists(String username);
    User findUser(String username);

    void saveUser(String username, String password, Boolean enabled, Set<Authority> authorities);
}
