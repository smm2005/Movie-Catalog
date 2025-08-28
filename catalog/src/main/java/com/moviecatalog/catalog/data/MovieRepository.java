package com.moviecatalog.catalog.data;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviecatalog.catalog.movie.*;

public interface MovieRepository extends JpaRepository<Movie, Long>{
    Page<Movie> findAll(Pageable pageable);
    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Optional<Movie> findById(Long id);
    Movie findByTitle(String title);
    Movie findByGenre(String genre);
}