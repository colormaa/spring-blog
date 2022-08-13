package com.blog.blog.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.blog.blog.model.BlogRepository;
import com.blog.blog.model.CategoryRepository;
import com.blog.blog.model.UserRepository;
import com.blog.blog.model.data.Admin;
import com.blog.blog.model.data.Blog;
import com.blog.blog.model.data.Category;
import com.blog.blog.model.data.User;

@Controller
public class HomeController {
    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/")
    public String index(Model model) {
        List<Blog> blogs = blogRepo.findAll();
        model.addAttribute("blogs", blogs);
        List<Category> categories = categoryRepo.findAll();
        HashMap<Integer, String> cats = new HashMap<>();
        for (Category cat : categories) {
            cats.put(cat.getId(), cat.getName());
        }

        model.addAttribute("cats", cats);
        model.addAttribute("blogsMost", blogs);
        model.addAttribute("blogsLast", blogs);

        HashMap<Integer, List<Blog>> blogByCategories = new HashMap<>();
        for (Category cat : categories) {
            Pageable pageableCat = PageRequest.of(0, 4);

            List<Blog> blogCat = blogRepo.findAllByCategoryId(Integer.toString(cat.getId()), pageableCat);
            blogByCategories.put(cat.getId(), blogCat);
        }
        model.addAttribute("blogByCategories", blogByCategories);

        return "index";
    }

    @GetMapping("/login")
    public String login(User user) {
        return "login";
    }

    @GetMapping("/detail/{slug}")
    public String detail(@PathVariable String slug, Model model) {
        Blog blog = blogRepo.findBySlug(slug);
        model.addAttribute("blog", blog);
        User author = userRepo.getReferenceById(Integer.parseInt(blog.getAuthorId()));
        model.addAttribute("authorName", author.getUsername());
        System.out.print("blog " + blog);
        Category category = categoryRepo.getReferenceById(Integer.parseInt(blog.getCategoryId()));
        model.addAttribute("categoryName", category.getName());
        return "detail";
    }

    @GetMapping("/category/{name}")
    public String categoryByName(@PathVariable String name, Model model) {
        model.addAttribute("categoryName", name);
        Pageable pageableCat = PageRequest.of(0, 50);
        Category cat = categoryRepo.findByName(name);

        List<Blog> blogCat = blogRepo.findAllByCategoryId(Integer.toString(cat.getId()), pageableCat);
        System.out.print("blogcat length " + blogCat.size());
        model.addAttribute("blogsByCat", blogCat);

        List<Category> subCategories = categoryRepo.findAllByParentId(cat.getId());
        System.out.print("cateogry " + subCategories.size());
        model.addAttribute("subCategories", subCategories);
        return "category";
    }
}
