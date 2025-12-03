package com.moviecatalog.catalog.recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.handlers.MovieHandler;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class MovieRecommender {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FavouriteRepository favouriteRepository;

    private MovieHandler movieHandler = new MovieHandler();

    public double getAverageRating(){
        double averageRating = 0.0;
        List<Favourite> recentFavourites = favouriteRepository.findFirst5ByOrderByIdDesc();
        for (Favourite favourite : recentFavourites){
            averageRating += (double) favourite.getMovie().getRating();
        }
        int length = recentFavourites.size();
        averageRating = averageRating / ((double) length);
        return averageRating;
    }

    public double getAngleBetween(Movie mov){
        List<Favourite> recentFavourites = favouriteRepository.findFirst5ByOrderByIdDesc();
        int length = recentFavourites.size();
        double averageScore = 0.0;
        double averageRating = getAverageRating();
        for (Favourite favourite : recentFavourites){
            averageScore += (double) movieHandler.getGenreScore(favourite.getMovie().getGenre());
        }
        averageScore = averageScore / ((double) length);
        FeatureVector favouriteVector = new FeatureVector(averageScore, averageRating);
        return favouriteVector.angleBetween(
            new FeatureVector(
                (double) (movieHandler.getGenreScore(mov.getGenre())),
                (double) (mov.getRating()))
        );
    }

    public List<Movie> getTopFiveRecs(){
        double averageRating = getAverageRating() > 0.0 ? getAverageRating() : 8.5;
        float lower = (float) Math.floor(averageRating);
        float upper = (float) Math.ceil(averageRating);
        List<Movie> movies = movieRepository
               .findFirst50ByRatingBetween(lower, upper)
               .stream()
               .filter((movie) -> (favouriteRepository.findByMovie(movie).isEmpty()))
               .sorted((mov1, mov2) -> Double.compare(getAngleBetween(mov1), getAngleBetween(mov2)))
               .collect(Collectors.toList())
               .subList(0, 5);
        return movies;
    }


}
