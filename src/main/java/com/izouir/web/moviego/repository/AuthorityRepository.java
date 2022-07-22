package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByAuthorityIgnoreCase(String authority);

}
