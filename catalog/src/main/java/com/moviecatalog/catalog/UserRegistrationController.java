package com.moviecatalog.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.user.RegistrationForm;
import com.moviecatalog.catalog.user.User;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value="/register")
@SessionAttributes("login")
public class UserRegistrationController {

    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public UserRegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String registerUser(RegistrationForm form, SessionStatus status) {
        @Valid User newUser = form.toUser(passwordEncoder);
        userRepository.save(newUser);
        log.info("User registered: " + newUser);
        log.info(newUser.getAuthorities().toString());
        status.setComplete();

        return "redirect:/login";
    }
    
}