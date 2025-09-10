package com.moviecatalog.catalog.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.user.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository){
        return username -> {
            User user = userRepository.findByUsername(username);
            if (user != null){
                return user;
            }
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authorize) -> authorize
        .requestMatchers("/movies", "/movies**", "/favourites", "/favourites**").hasRole("USER")
        .requestMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated())
        .formLogin((login) -> login
        .loginPage("/login")
        .defaultSuccessUrl("/"))
        .userDetailsService(userDetailsService(null))
        .logout((logout) -> logout
        .logoutSuccessUrl("/"))
        .build();
    }

}
