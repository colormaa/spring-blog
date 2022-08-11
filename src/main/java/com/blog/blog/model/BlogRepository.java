
package com.blog.blog.model;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.blog.model.data.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Blog findBySlug(String slug);

    Blog findTopByOrderByIdDesc();

    List<Blog> findAllByOrderByIdDesc(Pageable pageable);

    List<Blog> findAllByOrderByViewCountDesc(Pageable pageableMost);

    List<Blog> findAllByCategoryIdOrderByIdDesc(int id, Pageable pageable);

    List<Blog> findAllByCategoryId(String id, Pageable pageableCat);

}
