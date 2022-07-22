package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository USER_REPOSITORY;

    public UserServiceImpl(UserRepository USER_REPOSITORY) {
        this.USER_REPOSITORY = USER_REPOSITORY;
    }

    @Override
    @Transactional
    public User getUser(String username) {
        return USER_REPOSITORY.findByUsernameIgnoreCase(username);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        USER_REPOSITORY.save(user);
    }
}
