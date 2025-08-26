package com.moviecatalog.catalog.data;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moviecatalog.catalog.movie.Favourite;

public interface FavouriteRepository extends JpaRepository<Favourite, Long>{
    
}
