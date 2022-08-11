package com.blog.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.blog.model.AdminRepository;
import com.blog.blog.model.UserRepository;
import com.blog.blog.model.data.Admin;
import com.blog.blog.model.data.User;

@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private AdminRepository adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        Admin admin = adminRepo.findByUsername(username);
        System.out.println("admin " + admin);
        if (user != null) {
            return user;
        }
        if (admin != null) {
            return admin;
        }

        throw new UsernameNotFoundException("User: " + username + " not found.");
    }

}
