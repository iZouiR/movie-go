package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityIgnoreCase(String authority);
}