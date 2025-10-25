package com.moviecatalog.catalog.controllers.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.TokenRepository;
import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.security.Token;
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
    private TokenRepository tokenRepository;

    public LoginController(TokenService tokenService, UserRepository userRepository, AuthenticationManager authenticationManager, TokenRepository tokenRepository){
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest loginRequest){
        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));
        User user = this.userRepository.findByUsername(loginRequest.getUsername()).get();
        Map<String, String> tokenPair = tokenService.generateTokenPair(user);
        String accessToken = tokenPair.get("access_token");
        String refreshToken = tokenPair.get("refresh_token");
        this.tokenRepository.save(Token.builder()
            .token(refreshToken)
            .expiry(tokenService.extractExpiration(refreshToken))
            .userId(user.getId()).build());
        return ResponseEntity.ok(AuthenticationResponse.builder().token(accessToken).build());
    }

}
