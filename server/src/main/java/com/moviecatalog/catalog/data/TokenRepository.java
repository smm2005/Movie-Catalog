package com.moviecatalog.catalog.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.moviecatalog.catalog.security.Token;

import jakarta.transaction.Transactional;

public interface TokenRepository extends JpaRepository<Token, Long>{
    Token findByUserId(Long userId);
    
    @Modifying
    @Transactional
    @Query("UPDATE tokens t SET t.token = ?1 WHERE t.userId = ?2")
    void updateRefreshToken(String newToken, Long userId);
}
