package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.User;

public interface UserService {

    User getUser(String username);
    void saveUser(User user);

}
