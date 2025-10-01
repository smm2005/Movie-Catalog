package com.moviecatalog.catalog.data;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

public interface FavouriteRepository extends JpaRepository<Favourite, Long>{
    Page<Favourite> findAll(Pageable pageable);

    // List<Favourite> findFirst5ByOrderByCreatedDesc();

    List<Favourite> findAllByUser(User referenceById);

    Favourite findByMovie(Movie currentMovie);
}
