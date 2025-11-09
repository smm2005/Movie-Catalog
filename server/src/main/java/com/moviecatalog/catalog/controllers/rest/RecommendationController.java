package com.moviecatalog.catalog.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.handlers.MovieHandler;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.recommender.MovieRecommender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path="/api/recs", produces="application/json")
@CrossOrigin
public class RecommendationController {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private MovieRecommender movieRecommender;

    @GetMapping
    public ResponseEntity<List<Movie>> getTopFiveRecommendations(){
        List<Movie> recommendations = movieRecommender.getTopFiveRecs();
        for (Movie recommendation : recommendations){
            log.info(recommendation.toString());
        }
        return ResponseEntity.ok(recommendations);
    }

}
