package com.example.blog.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(String name);
    boolean existsByUsername(String name);
    Optional<User> findByUsername(String username);
}
