package com.moviecatalog.catalog.controllers.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.TransactionSystemException;
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

    @PostMapping
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegistrationForm form) {
        @Valid User newUser = form.toUser(passwordEncoder);
        try{
            userRepository.save(newUser);
            return ResponseEntity.ok("User has been registered");
        }
        catch (TransactionSystemException exception){
            ConstraintViolationException currentException = (ConstraintViolationException) exception.getRootCause();
            ConstraintViolation<?> firstConstraintViolation = (ConstraintViolation<?>) currentException.getConstraintViolations().toArray()[0];
            return ResponseEntity.badRequest().body(firstConstraintViolation.getMessage());
        }
    }
}