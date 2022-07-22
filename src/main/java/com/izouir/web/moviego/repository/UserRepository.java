package com.izouir.web.moviego.repository;

import com.izouir.web.moviego.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsernameIgnoreCase(String username);

}
