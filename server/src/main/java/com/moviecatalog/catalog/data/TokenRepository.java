package com.moviecatalog.catalog.data;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.moviecatalog.catalog.security.Token;
import com.moviecatalog.catalog.user.User;

public interface TokenRepository extends JpaRepository<Token, Long>{
    Token findByUserId(Long userId);
    
    @Modifying
    @Query("UPDATE tokens t SET t.token = ?1 WHERE t.userId = ?2")
    int updateRefreshToken(String newToken, Long userId);
}
