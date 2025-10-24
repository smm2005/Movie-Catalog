package com.moviecatalog.catalog.service;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.moviecatalog.catalog.security.RSAKeyProperties;
import com.moviecatalog.catalog.user.User;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
    
    private final RSAKeyProperties rsaKeys;

    public JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKeys.publicKey()).privateKey(rsaKeys.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks); 
    }

    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
    }

    public String extractUsername(String token){
        return extractClaim(token, (claim) -> claim.getSubject());
    }

    public Date extractExpiration(String token){
        return extractClaim(token, (claim) -> claim.getExpiration());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaims(token);
        return claimResolver.apply(claims);
    }

    public boolean isTokenValid(String token, User user){
        return (extractUsername(token).equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        Instant expiration = extractClaim(token, (claim) -> claim.getExpiration().toInstant());
        return expiration.isBefore(Instant.now());
    }

    private Claims getAllClaims(String token){
        RSAPublicKey publicKey = rsaKeys.publicKey();
        return Jwts.parser().verifyWith(publicKey).build().parseSignedClaims(token).getPayload();
    }
    
    public String generateAccessToken(User user) {
        Instant currentTime = Instant.now();
        String scope = user.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .subject(user.getUsername())
                .issuedAt(currentTime)
                .expiresAt(currentTime.plus(10, ChronoUnit.SECONDS))
                .claim("scope", scope)
                .build();
        return jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String generateRefreshToken(User user) {
        Instant currentTime = Instant.now();
        String scope = user.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .subject(user.getUsername())
                .issuedAt(currentTime)
                .expiresAt(currentTime.plus(7, ChronoUnit.DAYS))
                .claim("scope", scope)
                .build();
        return jwtEncoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Map<String, String> generateTokenPair(User user){
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", generateAccessToken(user));
        tokens.put("refresh_token", generateRefreshToken(user));
        return tokens;
    }


}
