package com.moviecatalog.catalog.controllers.rest;

import com.moviecatalog.catalog.user.User;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.UserRepository;

@Slf4j
@RestController
@RequestMapping(path="/api/user", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int ident){
        return userRepository.getReferenceById(Integer.toUnsignedLong(ident));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") int ident){
        userRepository.deleteById(Integer.toUnsignedLong(ident));
    }
}