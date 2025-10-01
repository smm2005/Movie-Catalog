package com.moviecatalog.catalog.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
        registry.addViewController("/logout");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/card**").allowedOrigins("http://localhost:8080").allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS").allowedHeaders("*").allowCredentials(true).maxAge(3600);
        registry.addMapping("/api**").allowedOriginPatterns("http://localhost:8080").allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS").allowedHeaders("*").allowCredentials(true).maxAge(3600);
    }
}
