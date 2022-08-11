package com.blog.blog.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.blog.model.data.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category findByName(String name);

    List<Category> findAllByParentId(int id);

}
