package com.moviecatalog.catalog.recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@NoArgsConstructor
@Component
public class Recommender {
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FavouriteRepository favouriteList;

    public List<Movie> getMovies(){
        List<Movie> movies = favouriteList
        .findAll()
        .stream()
        .map(favourite -> favourite.getMovie())
        .collect(Collectors.toList());
        int moviesLength = movies.size();
        if (moviesLength <= 5){
            return movies;
        }
        else{
            return movies.subList(moviesLength - 5, moviesLength);   
        }
    }

    public Float getRatingAverage(){
        List<Float> ratings = getMovies()
        .stream()
        .map(movie -> movie.getRating())
        .collect(Collectors.toList());
        float total = 0;
        for (Float rating: ratings){
            total = total + rating;
        }
        return (float) (Math.round((total / ratings.size()) * 10.0) / 10.0);
    }

    public List<Movie> getRecommendations(){
        List<Movie> movies = movieRepository.findFirst5ByRating(getRatingAverage());
        System.out.println(movies);
        return movies;
    }

}
