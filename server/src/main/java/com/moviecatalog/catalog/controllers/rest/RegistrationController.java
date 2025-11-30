package com.moviecatalog.catalog.controllers.rest;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.TokenRepository;
import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.security.Token;
import com.moviecatalog.catalog.security.auth.AuthenticationResponse;
import com.moviecatalog.catalog.user.RegistrationForm;
import com.moviecatalog.catalog.user.User;
import com.moviecatalog.catalog.service.TokenService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path="/api/register", produces="application/json")
@CrossOrigin
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationForm form) {
        @Valid User newUser = form.toUser(passwordEncoder);
        try{
            userRepository.save(newUser);
            return ResponseEntity.ok("User has been registered");
        }
        catch (ConstraintViolationException exception){
            Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
            String firstConstraintViolation = constraintViolations.toArray()[0].toString();
            return ResponseEntity.badRequest().body(firstConstraintViolation);
        }
    }
}