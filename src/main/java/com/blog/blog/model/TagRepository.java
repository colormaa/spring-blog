package com.blog.blog.model;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog.model.data.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
}
