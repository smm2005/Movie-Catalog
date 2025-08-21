package com.moviecatalog.catalog.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.user.User;
import com.moviecatalog.catalog.user.UserDetailsService;

@Component
public class UserHandler implements UserDetailsService {
    
    @Autowired
    UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if (user != null){
            return user;
        }
        throw new UsernameNotFoundException("User '" + username + "' not found");
    }
    
}
