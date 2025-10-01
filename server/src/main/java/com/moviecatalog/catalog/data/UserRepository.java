package com.moviecatalog.catalog.data;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.moviecatalog.catalog.user.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
    void deleteById(Long id);
}