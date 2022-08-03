package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.entity.User;
import com.izouir.web.moviego.exception.UserNotFoundException;
import com.izouir.web.moviego.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean userExists(final String username) {
        return userRepository.findByUsernameIgnoreCase(username).isPresent();
    }

    public User findUser(final String username) {
        return userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new UserNotFoundException("User with username=" + username + " was not found"));
    }

    public void saveUser(final String username, final String password, final Boolean enabled, final Set<Authority> authorities) {
        final User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(enabled);
        user.setAuthorities(authorities);
        userRepository.save(user);
    }
}
