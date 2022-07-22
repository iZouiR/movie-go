package com.izouir.web.moviego.service;

import com.izouir.web.moviego.entity.Authority;
import com.izouir.web.moviego.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository AUTHORITY_REPOSITORY;

    public AuthorityServiceImpl(@Autowired AuthorityRepository AUTHORITY_REPOSITORY) {
        this.AUTHORITY_REPOSITORY = AUTHORITY_REPOSITORY;
    }

    @Override
    @Transactional
    public Authority getAuthority(String authority) {
        return AUTHORITY_REPOSITORY.findByAuthorityIgnoreCase(authority);
    }
}
