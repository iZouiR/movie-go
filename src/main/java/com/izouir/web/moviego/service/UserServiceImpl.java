package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.UserNotFoundException;
import com.izouir.web.moviego.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUser(final String username) throws UserNotFoundException {
        return userRepository.findByUsernameIgnoreCase(username).orElseThrow(
                () -> new UserNotFoundException("User with username=" + username + " was not found"));
    }

    @Override
    @Transactional
    public void saveUser(final String username, final String password, final Boolean enabled, final Set<Authority> authorities) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEnabled(enabled);
        user.setAuthorities(authorities);
        userRepository.save(user);
    }
}
