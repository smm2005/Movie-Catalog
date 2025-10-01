package com.moviecatalog.catalog.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.user.RegistrationForm;
import com.moviecatalog.catalog.user.User;
import com.moviecatalog.catalog.security.SecurityConfig;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/register")
public class UserRegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String registerUser(RegistrationForm form, SessionStatus status, Model model) {
        @Valid User newUser = form.toUser(passwordEncoder);
        userRepository.save(newUser);
        model.addAttribute("user", newUser.getRealname());
        log.info("User registered: " + newUser);
        log.info(userRepository.findByUsername(newUser.getUsername()).toString());
        log.info(newUser.getAuthorities().toString());
        return "redirect:/login";
    }
}