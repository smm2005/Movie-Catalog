package com.moviecatalog.catalog.data;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.moviecatalog.catalog.user.User;

import io.micrometer.common.lang.Nullable;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    
    void deleteById(Long id);
}