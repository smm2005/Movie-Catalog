package com.moviecatalog.catalog.recommender;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;
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
import java.util.Collections;
import java.util.Comparator;

@Component
@Data
@NoArgsConstructor
public class TitleRecommender implements Recommender{
    
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

    public String getFirstTwoWords(String title){
        String[] words = title.split(" ");
        return words[0] + " " + words[1];
    }

    @Override
    public String getMetric(){
        List<String> titles = getMovies()
        .stream()
        .map(movie -> getFirstTwoWords(movie.getTitle()))
        .collect(Collectors.toList());
        TreeMap<String, Integer> titleCount = new TreeMap<>();
        for (String title : titles){
            if (titleCount.containsKey(title)){
                titleCount.put(title, titleCount.get(title) + 1);
            }
            else{
                titleCount.put(title, 1);
            }
        }
        TreeSet<String> sortedTitles = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (titleCount.get(o1) > titleCount.get(o2)){
                    return 1;
                }
                else if (titleCount.get(o1) < titleCount.get(o2)){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        });
        sortedTitles.addAll(titleCount.keySet());
        return sortedTitles.first();
    }

    @Override
    public List<Movie> getRecommendations(){
        List<Movie> movies = movieRepository.findFirst5ByTitleContainingIgnoreCase(getMetric());
        System.out.println(movies);
        return movies;
    }

}
