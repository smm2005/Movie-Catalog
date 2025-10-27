package com.moviecatalog.catalog.controllers.rest;

import com.moviecatalog.catalog.user.User;

import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.service.TokenService;

@Slf4j
@RestController
@RequestMapping(path="/api/user", produces="application/json")
@CrossOrigin
public class UserController {

    private UserRepository userRepository;
    private TokenService tokenService;

    public UserController(UserRepository userRepository, TokenService tokenService){
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @GetMapping
    public ResponseEntity<NamesRecord> getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken){
        String realAccessToken = accessToken.split(" ")[1];
        String username = tokenService.extractUsername(realAccessToken);
        String realname = userRepository.findByUsername(username).get().getRealname();
        return ResponseEntity.ok(new NamesRecord(realname, username));
    }

    @GetMapping("/all")
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

    public record NamesRecord(String realname, String username) {}
}