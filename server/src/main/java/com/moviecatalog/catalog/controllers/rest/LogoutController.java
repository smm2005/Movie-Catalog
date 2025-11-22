package com.moviecatalog.catalog.controllers.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.TokenRepository;
import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.security.Token;
import com.moviecatalog.catalog.security.auth.AuthenticationRequest;
import com.moviecatalog.catalog.service.TokenService;
import com.moviecatalog.catalog.user.User;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path="/api/logout", produces="application/json")
@CrossOrigin
public class LogoutController {

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping
    public void logout(@RequestBody AccessTokenRecord tokenBody) {
        String accessToken = tokenBody.token();
        try{
            User currentUser = userRepository.findByUsername(tokenService.extractUsername(accessToken)).get();
            tokenRepository.revokeToken(currentUser.getId());
        }
        catch (ExpiredJwtException e){
            String username = e.getClaims().getSubject();
            tokenRepository.revokeToken(userRepository.findByUsername(username).get().getId());
        }
        catch (Exception e){
            ;
        }
    }

    public record AccessTokenRecord(String token){}; 
    
}
