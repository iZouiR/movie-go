package com.izouir.web.moviego.security;

import com.izouir.web.moviego.exception.UserNotFoundException;
import com.izouir.web.moviego.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return new CustomUserDetails(userRepository.findByUsernameIgnoreCase(username).orElseThrow(
                () -> new UserNotFoundException("User with username=" + username + " was not found")));
    }
}
