
package com.blog.blog.model.data;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "blog")
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(min = 2, message = "Title must be at least 2 character long.")
    private String title;
    @Size(min = 5, message = "Content must be at least 5 characters long")
    private String content;
    private String brief;
    private String subtext;
    private String minutes;

    private String image;

    @Column(name = "view_count")
    private int viewCount;

    private String slug;
    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Pattern(regexp = "^[1-9][0-9]*", message = "Choose category")
    @Column(name = "category_id")
    private String categoryId;

}
