package com.moviecatalog.catalog.recommender;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.handlers.MovieHandler;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;

public class MovieRecommender {

    private Favourite favourite;
    private MovieHandler movieHandler = new MovieHandler();
    private FeatureVector featureVector = new FeatureVector(
            (double) movieHandler.getGenreScore(favourite.getMovie().getGenre()),
            (double) favourite.getMovie().getRating());

    @Autowired
    private MovieRepository movieRepository;

    public MovieRecommender(Favourite myFavourite){
        this.favourite = myFavourite;
    }

    public double getAngleBetween(Movie mov){
        return featureVector.angleBetween(
            new FeatureVector(
                (double) (movieHandler.getGenreScore(mov.getGenre())),
                (double) (mov.getRating()))
        );
    }

    public List<Movie> getTopFiveRecs(){
        return movieRepository
               .findAll()
               .stream()
               .filter((movie) -> getAngleBetween(movie) > 0)
               .sorted((mov1, mov2) -> Double.compare(getAngleBetween(mov1), getAngleBetween(mov2)))
               .collect(Collectors.toList())
               .subList(0, 5);
    }


}
