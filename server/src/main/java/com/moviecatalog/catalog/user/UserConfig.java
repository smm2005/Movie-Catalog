package com.moviecatalog.catalog.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.moviecatalog.catalog.data.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
public class UserConfig {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            User user = this.userRepository.findByUsername(username).get();
            if (user != null){
                return user;
            }
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        };
    }
}
