package com.blog.blog;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.blog.blog.model.BlogRepository;
import com.blog.blog.model.CategoryRepository;
import com.blog.blog.model.UserRepository;
import com.blog.blog.model.data.Blog;
import com.blog.blog.model.data.Category;
import com.blog.blog.model.data.User;

@ControllerAdvice
public class Common {
    @Autowired
    private CategoryRepository categoryRepo;
    @Autowired
    private BlogRepository blogRepo;

    @Autowired
    private UserRepository userRepo;

    @ModelAttribute
    public void sharedData(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("principal", principal.getName());
            User userLoggedIn = userRepo.findByUsername(principal.getName());
            System.out.println("pringical user " + userLoggedIn);
            model.addAttribute("principalUser", userLoggedIn);
        }
        List<Category> categories = categoryRepo.findAllByParentId(-1);
        model.addAttribute("commonCategories", categories);

        Blog blogLatest = blogRepo.findTopByOrderByIdDesc();
        model.addAttribute("latest", blogLatest);

        // get second and third latest article
        int perPage = 1;
        int page = 1;
        Pageable pageable2 = PageRequest.of(page, perPage);
        List<Blog> blog2 = blogRepo.findAllByOrderByIdDesc(pageable2);

        page = 2;
        Pageable pageable3 = PageRequest.of(page, perPage);
        List<Blog> blog3 = blogRepo.findAllByOrderByIdDesc(pageable3);
        blog2.addAll(blog3);
        model.addAttribute("latest23", blog2);

        // most read
        Pageable pageableMost = PageRequest.of(0, 3);
        List<Blog> blogMost = blogRepo.findAllByOrderByViewCountDesc(pageableMost);
        model.addAttribute("blogMostRead", blogMost);
    }
}
