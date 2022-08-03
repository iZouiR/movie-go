package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.exception.AuthorityNotFoundException;
import com.izouir.web.moviego.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorityService(final AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority findAuthority(final Long id) {
        return authorityRepository.findById(id)
                .orElseThrow(() -> new AuthorityNotFoundException("Authority with id=" + id + " was not found"));
    }
}
