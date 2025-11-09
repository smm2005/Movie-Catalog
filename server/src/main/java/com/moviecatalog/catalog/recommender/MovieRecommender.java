package com.moviecatalog.catalog.recommender;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

    public double getAngleBetween(Movie mov){
        List<Favourite> recentFavourites = favouriteRepository.findFirst5ByOrderByIdDesc();
        int length = recentFavourites.size();
        double averageScore = 0.0;
        double averageRating = 0.0;
        for (Favourite favourite : recentFavourites){
            averageScore += (double) movieHandler.getGenreScore(favourite.getMovie().getGenre());
            averageRating += (double) favourite.getMovie().getRating();
        }
        averageScore = averageScore / ((double) length);
        averageRating = averageRating / ((double) length);
        FeatureVector favouriteVector = new FeatureVector(averageScore, averageRating);
        return favouriteVector.angleBetween(
            new FeatureVector(
                (double) (movieHandler.getGenreScore(mov.getGenre())),
                (double) (mov.getRating()))
        );
    }

    public List<Movie> getTopFiveRecs(){
        List<Movie> movies = movieRepository
               .findAll()
               .stream()
               .filter((movie) -> getAngleBetween(movie) > 0)
               .sorted((mov1, mov2) -> Double.compare(getAngleBetween(mov1), getAngleBetween(mov2)))
               .collect(Collectors.toList())
               .subList(0, 5);
        return movies;
    }


}
