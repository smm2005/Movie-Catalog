package com.moviecatalog.catalog.recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Recommender {
    
    @Autowired
    private MovieRepository movieRepository;

    private FavouriteRepository favouriteList;

    public List<Movie> getMovies(){
        List<Movie> movies = favouriteList
        .findAll()
        .stream()
        .map(favourite -> movieRepository.findById(favourite.getMovie().getId()).get())
        .collect(Collectors.toList());
        return movies;
    }

    public List<Float> getRatings(){
        List<Float> ratings = getMovies()
        .stream()
        .map(movie -> movie.getRating())
        .collect(Collectors.toList());
        return ratings;
    }

    

}
