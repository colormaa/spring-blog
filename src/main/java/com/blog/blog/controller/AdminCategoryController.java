package com.blog.blog.controller;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.blog.model.CategoryRepository;
import com.blog.blog.model.data.Category;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryRepo.findAll();
        HashMap<Integer, String> cats = new HashMap<>();
        for (Category cat : categories) {
            cats.put(cat.getId(), cat.getName());
        }
        model.addAttribute("categories", categories);
        model.addAttribute("cats", cats);
        return "admin/category/index";
    }

    @GetMapping("/add")
    public String add(Category category, Model model) {
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        return "admin/category/add";
    }

    @PostMapping("/add")
    public String add(@Valid Category category, Model model, RedirectAttributes redirectAttributes,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/category/add";
        }
        redirectAttributes.addFlashAttribute("message", "Category added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        Category exist = categoryRepo.findByName(category.getName());
        if (exist != null) {
            redirectAttributes.addFlashAttribute("message", "Category exist");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        } else {
            categoryRepo.save(category);
        }

        return "redirect:/admin/category/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Category currentCategory = categoryRepo.getReferenceById(id);

        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("category", currentCategory);
        return "admin/category/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Category category, Model model, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "admin/category/edit";
        }
        redirectAttributes.addFlashAttribute("message", "Category edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        Category exist = categoryRepo.findByName(category.getName());

        if (exist != null) {
            redirectAttributes.addFlashAttribute("message", "Category name exist");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        } else {
            categoryRepo.save(category);
        }

        return "redirect:/admin/category/edit/" + category.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "Successfully deleted");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        categoryRepo.deleteById(id);
        return "redirect:/admin/category";

    }
}
