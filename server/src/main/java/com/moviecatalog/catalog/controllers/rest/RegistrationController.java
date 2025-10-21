package com.moviecatalog.catalog.controllers.rest;

import java.util.Map;

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
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegistrationForm form) {
        @Valid User newUser = form.toUser(passwordEncoder);
        userRepository.save(newUser);
        Map<String,String> tokenPair = tokenService.generateTokenPair(newUser);
        String refreshToken = tokenPair.get("refresh_token");
        tokenRepository.save(Token.builder()
            .token(refreshToken)
            .expiry(tokenService.extractExpiration(refreshToken))
            .userId(newUser.getId()).build());
        AuthenticationResponse response = new AuthenticationResponse(tokenPair.get("access_token"));
        return ResponseEntity.ok(response);
    }
}