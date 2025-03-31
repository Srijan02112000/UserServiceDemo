package com.task.UserService.repository;


import com.task.UserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByname(String name);
    boolean existsByEmail(String email);
}