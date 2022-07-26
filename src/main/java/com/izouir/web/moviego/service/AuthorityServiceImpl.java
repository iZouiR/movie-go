package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.exception.AuthorityNotFoundException;
import com.izouir.web.moviego.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository AUTHORITY_REPOSITORY;

    public AuthorityServiceImpl(@Autowired AuthorityRepository AUTHORITY_REPOSITORY) {
        this.AUTHORITY_REPOSITORY = AUTHORITY_REPOSITORY;
    }

    @Override
    @Transactional
    public Authority findAuthority(String authority) throws AuthorityNotFoundException {
        Optional<Authority> foundAuthority = AUTHORITY_REPOSITORY.findByAuthorityIgnoreCase(authority);
        if (foundAuthority.isEmpty()) {
            throw new AuthorityNotFoundException(String.format("Authority with name=%s was not found", authority));
        }
        return foundAuthority.get();
    }
}
