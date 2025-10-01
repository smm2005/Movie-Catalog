package com.moviecatalog.catalog.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreRecommender implements Recommender{
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private FavouriteRepository favouriteList;

    @Override
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

    @Override
    public String getMetric(){
        List<String> genres = getMovies()
        .stream()
        .map(movie -> movie.getGenre())
        .collect(Collectors.toList());
        TreeMap<String, Integer> genreCount = new TreeMap<>();
        for (String genre : genres){
            if (genre.contains(",")){
                for (String subGenre : genre.split(", ")){
                    if (genreCount.containsKey(subGenre)){
                        genreCount.put(subGenre, genreCount.get(subGenre) + 1);
                    }
                    else{
                        genreCount.put(subGenre, 1);
                    }
                }
            }
            else{
                if (genreCount.containsKey(genre)){
                    genreCount.put(genre, genreCount.get(genre) + 1);
                }
                else{
                    genreCount.put(genre, 1);
                }
            }
        }
        String[] genreKeys = genreCount.keySet().toArray(new String[0]);
        int max = -1;
        String coreGenre = "";
        for (String genre: genreKeys){
            if (genreCount.get(genre) > max){
                max = genreCount.get(genre);
                coreGenre = genre;
            }
        }
        return coreGenre;
    }

    @Override
    public List<Movie> getRecommendations(){
        List<Movie> movies = movieRepository.findFirst5ByGenre(getMetric());
        System.out.println(movies);
        return movies;
    }

}
