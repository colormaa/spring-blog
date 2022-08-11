package com.blog.blog.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog.model.data.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {

    Admin findByUsername(String username);

}
