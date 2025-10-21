package com.moviecatalog.catalog.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.security.auth.AuthenticationRequest;
import com.moviecatalog.catalog.security.auth.AuthenticationResponse;
import com.moviecatalog.catalog.service.TokenService;
import com.moviecatalog.catalog.user.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path="/api/login", produces="application/json")
@CrossOrigin
public class LoginController {
    
    private final TokenService tokenService;
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;

    public LoginController(TokenService tokenService, UserRepository userRepository, AuthenticationManager authenticationManager){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest){
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        User user = this.userRepository.findByUsername(loginRequest.getUsername()).get();
        log.info("User " + user.getUsername() + " logged in");
        return ResponseEntity.ok(AuthenticationResponse.builder().build());
    }

}
