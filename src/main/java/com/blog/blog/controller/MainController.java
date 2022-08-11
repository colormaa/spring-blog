package com.blog.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.blog.model.BlogRepository;
import com.blog.blog.model.data.Blog;

@Controller
public class MainController {
    @Autowired
    private BlogRepository blogRepo;

    @GetMapping("/jj")
    public String index(Model model) {
        List<Blog> blogs = blogRepo.findAll();
        model.addAttribute("blogs", blogs);
        return "index";
    }
}