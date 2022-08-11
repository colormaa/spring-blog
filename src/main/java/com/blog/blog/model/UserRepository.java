package com.blog.blog.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog.model.data.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByUsernameOrEmail(String username, String email);
}
