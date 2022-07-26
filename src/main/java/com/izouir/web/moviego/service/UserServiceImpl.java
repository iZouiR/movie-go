package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.UserNotFoundException;
import com.izouir.web.moviego.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository USER_REPOSITORY;

    public UserServiceImpl(@Autowired UserRepository USER_REPOSITORY) {
        this.USER_REPOSITORY = USER_REPOSITORY;
    }

    @Override
    @Transactional
    public User findUser(String username) throws UserNotFoundException {
        Optional<User> foundUser = USER_REPOSITORY.findByUsernameIgnoreCase(username);
        if (foundUser.isEmpty()) {
            throw new UserNotFoundException(String.format("User with username=%s was not found", username));
        }
        return foundUser.get();
    }

    @Override
    @Transactional
    public void saveUser(String username, String password, Boolean enabled, List<Authority> authorities) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setAuthorities(authorities);
        USER_REPOSITORY.save(user);
    }
}
