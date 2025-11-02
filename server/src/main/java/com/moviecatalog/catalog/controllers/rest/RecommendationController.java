package com.moviecatalog.catalog.controllers.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.recommender.MovieRecommender;

@RestController
@RequestMapping(path="/api/recs", produces="application/json")
@CrossOrigin
public class RecommendationController {

    @Autowired
    private FavouriteRepository favouriteRepository;

    private MovieRecommender movieRecommender = new MovieRecommender(favouriteRepository.findAll().getLast()); 

    @GetMapping
    public ResponseEntity<List<Movie>> getTopFiveRecommendations(){
        return ResponseEntity.ok(movieRecommender.getTopFiveRecs());
    }

}
