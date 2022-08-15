package com.blog.blog.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.blog.model.BlogRepository;
import com.blog.blog.model.CategoryRepository;
import com.blog.blog.model.UserRepository;
import com.blog.blog.model.data.Blog;
import com.blog.blog.model.data.Category;
import com.blog.blog.service.MetadataService;

@Controller
public class UserAuthController {
    @Autowired
    private MetadataService metadataService;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BlogRepository blogRepo;
    @Autowired
    private CategoryRepository categoryRepo;

    @GetMapping("/addPost")
    public String addPost(Blog blog, Model model) {
        List<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        return "addPost";
    }

    private static String getExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            String extension = fileName.substring(index + 1);
            return extension;
        }
        return "";

    }

    @PostMapping("/addPost")
    public String addPostBlog(@Valid Blog blog, BindingResult bindingResult,
            MultipartFile file,
            RedirectAttributes redirectAttributes,
            Model model) throws IOException {
        System.out.print("add post " + blog);
        if (bindingResult.hasErrors()) {
            return "/addPost";
        }
        Blog lastBlog = blogRepo.findTopByOrderByIdDesc();
        int lastIndex = 0;
        if (lastBlog != null) {
            lastIndex = lastBlog.getId();
        }
        Boolean fileOk = false;
        // byte[] bytes = file.getBytes();
        String filenamefull = file.getOriginalFilename();
        String filename = lastIndex + "." + getExtension(filenamefull);
        // Path path = Paths.get("src/main/resources/static/media/" + filename);

        if (filename.endsWith("jpg") || filename.endsWith("png") || filename.endsWith("jpeg")) {
            fileOk = true;
        }
        // String html = "<p>An <a href='http://example.com/'><b>example</b></a>
        // link.</p>";
        Document doc = Jsoup.parse(blog.getContent());
        // Element link = doc.select("").first();

        String text = doc.body().text();
        // System.out.print("text " + text);

        redirectAttributes.addFlashAttribute("message", "Page added");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        String slug = blog.getTitle().toLowerCase().replace(" ", "-");

        Blog blogExist = blogRepo.findBySlug(slug);
        if (!fileOk) {
            redirectAttributes.addFlashAttribute("message", "Image must be jpg or png");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("product", blog);
        }
        if (blogExist != null) {
            redirectAttributes.addFlashAttribute("message", "Title exist, please choose another title");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
            redirectAttributes.addFlashAttribute("blog", blog);
        } else {
            blog.setSlug(slug);
            int endIndex = text.length() > 200 ? 200 : text.length();
            String textEnd = text.length() > 200 ? " ... " : "";
            text = text.substring(0, endIndex) + textEnd;

            blog.setBrief(text);
            // -String filepath = metadataService.upload(file, filename);
            // -blog.setImage(filepath);

            blogRepo.save(blog);
            // Files.write(path, bytes);
        }

        return "redirect:/addPost";
    }

}
