package com.moviecatalog.catalog.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

import jakarta.transaction.Transactional;

public interface FavouriteRepository extends JpaRepository<Favourite, Long>{
    Page<Favourite> findAll(Pageable pageable);

    List<Favourite> findAllByUser(User referenceById);

    List<Favourite> findFirst5ByOrderByIdDesc();

    Optional<Favourite> findByMovie(Movie currentMovie);

    @Modifying
    @Transactional
    @Query("DELETE FROM favourites f WHERE f.id = ?1")
    void removeFavourite(Long favouriteId);
}
