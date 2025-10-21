package com.moviecatalog.catalog.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.moviecatalog.catalog.service.TokenService;
import com.moviecatalog.catalog.user.User;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal (@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        SecurityContext context = SecurityContextHolder.getContext();
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.split(" ")[1];
        try{
            String username = jwtService.extractUsername(jwt);
            if (username != null && context.getAuthentication() == null){
                User newUser = (User) this.userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, newUser)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(newUser, null, newUser.getAuthorities());
                    authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    context.setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }
        catch (ExpiredJwtException e){
            filterChain.doFilter(request, response);
        }
    }

}
