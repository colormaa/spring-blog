package com.blog.blog.controller;

import java.util.List;

import javax.naming.Binding;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.blog.model.TagRepository;
import com.blog.blog.model.data.Tag;

@Controller
@RequestMapping("/admin/tag")
public class AdminTagController {
    @Autowired
    private TagRepository tagRepo;

    @GetMapping
    public String index(Model model) {
        List<Tag> tags = tagRepo.findAll();
        model.addAttribute("tags", tags);
        return "admin/tag/index";
    }

    @GetMapping("/add")
    public String add(Tag tag, Model model) {
        return "admin/tag/add";
    }

    @PostMapping("/add")
    public String add(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/tag/add";
        }
        redirectAttributes.addFlashAttribute("message", "Tag has been added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        tagRepo.save(tag);

        return "redirect:/admin/tag/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Tag tag = tagRepo.getReferenceById(id);
        model.addAttribute("tag", tag);

        return "/admin/tag/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/tag/edit";
        }
        redirectAttributes.addFlashAttribute("message", "Tag edited");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        Tag tagCurrent = tagRepo.findByName(tag.getName());
        if (tagCurrent != null) {
            redirectAttributes.addFlashAttribute("message", "Tag Name exist.");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        } else {
            tagRepo.save(tag);
        }

        return "redirect:/admin/tag/edit/" + tag.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, RedirectAttributes redirectAttributes) {
        tagRepo.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Tag deleted successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/admin/tag";
    }
}
