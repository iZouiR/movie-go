package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.exception.AuthorityNotFoundException;
import com.izouir.web.moviego.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityServiceImpl(final AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Authority findAuthority(final String authority) throws AuthorityNotFoundException {
        final Optional<Authority> foundAuthority = authorityRepository.findByAuthorityIgnoreCase(authority);
        if (foundAuthority.isEmpty()) {
            throw new AuthorityNotFoundException(String.format("Authority with name=%s was not found", authority));
        }
        return foundAuthority.get();
    }
}
