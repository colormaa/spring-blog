package com.blog.blog.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.blog.model.UserRepository;
import com.blog.blog.model.data.User;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String register(User user) {
        return "register";
    }

    @PostMapping
    public String register(@Valid User user,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        redirectAttributes.addFlashAttribute("message", "User registered successfully");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        User userExist = userRepo.findByUsernameOrEmail(user.getUsername(), user.getEmail());
        if (userExist != null) {
            redirectAttributes.addFlashAttribute("message", "User with username or email already exist");
            redirectAttributes.addFlashAttribute("alertClass", "alert-danger");

        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setPhoneNumber("");
            userRepo.save(user);
        }

        return "redirect:/login";
    }
}
