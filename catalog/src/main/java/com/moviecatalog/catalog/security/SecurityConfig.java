package com.moviecatalog.catalog.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authorize) -> 
        authorize./*requestMatchers("/").hasRole("USER")
        .*/requestMatchers("/**").permitAll()
        .requestMatchers("/**.css").permitAll()
        .requestMatchers("/h2-console/**").permitAll()).build();
        /*.formLogin((login) -> login.loginPage("/login")
        .loginProcessingUrl("/authenticate")
        .failureUrl("/login?error=true")
        .defaultSuccessUrl("/", true)
        .permitAll()).build();*/
    }

}
