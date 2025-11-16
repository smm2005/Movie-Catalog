package com.moviecatalog.catalog.controllers.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Authentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.TokenRepository;
import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.security.auth.AuthenticationResponse;
import com.moviecatalog.catalog.service.TokenService;
import com.moviecatalog.catalog.user.User;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path="/api/auth", produces="application/json")
@CrossOrigin
public class AuthenticationController {
    
    @Autowired
    TokenService tokenService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshUser(@RequestBody AccessTokenRecord tokenRequest) {
        String accessToken = tokenRequest.token();
        try{
            Optional<User> currentUser = userRepository.findByUsername(tokenService.extractUsername(accessToken));
            if (currentUser.isPresent()){
                AuthenticationResponse tokenResponse = AuthenticationResponse.builder().token(accessToken).build();
                return ResponseEntity.ok(tokenResponse);
            }
            else{
                return ResponseEntity.badRequest().body("User has not been registered");
            }
        }
        catch (ExpiredJwtException e){
            Optional<User> currentUser = userRepository.findByUsername(e.getClaims().getSubject());
            if (currentUser.isEmpty()){
                return ResponseEntity.badRequest().body("User has not been registered");
            }
            User user = currentUser.get();
            String refreshToken = tokenRepository.findByUserId(user.getId()).getToken();
            if (tokenService.isTokenValid(refreshToken, user)){
                Map<String, String> newTokenPair = tokenService.generateTokenPair(user);
                String newAccessToken = newTokenPair.get("access_token");
                String newRefreshToken = newTokenPair.get("refresh_token");
                tokenRepository.updateRefreshToken(newRefreshToken, user.getId());
                AuthenticationResponse newTokenResponse = AuthenticationResponse.builder().token(newAccessToken).build();
                return ResponseEntity.ok(newTokenResponse);
            }
            else {
                return ResponseEntity.badRequest().body("Refresh token is not valid.");
            }
        }
    }

    public record AccessTokenRecord(String token){};

}
