package com.moviecatalog.catalog.controllers.rest;

import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.user.User;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.data.UserRepository;

@RestController
@RequestMapping(path="/api/movies", produces="application/json")
@CrossOrigin
public class MovieRESTController {

    private MovieRepository movieRepository;
    private UserRepository userRepository;
    private FavouriteRepository favouriteRepository;

    public MovieRESTController(MovieRepository movieRepository, UserRepository userRepository, FavouriteRepository favouriteRepository){
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.favouriteRepository = favouriteRepository;
    }

    @GetMapping
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @GetMapping(path="count", params={"search"})
    public int getMovieCount(@RequestParam String search) {
        return movieRepository.findByTitleContainingIgnoreCase(search, null).getContent().size();
    }
    
    
    @GetMapping(params="page")
    public Iterable<Movie> getMoviesGivenPageNumber(@RequestParam int page){
        return movieRepository.findAll(PageRequest.of(page, 30)).getContent();
    }

    @GetMapping(params={"page", "search"})
    public Iterable<Movie> getMoviesGivenSearch(@RequestParam int page, @RequestParam String search){
        return movieRepository.findByTitleContainingIgnoreCase(search, PageRequest.of(page, 30)).getContent();
    }

    @GetMapping(params={"id"})
    public ResponseEntity<Movie> getMovieById(@RequestParam int id){
        Movie movie = movieRepository.findById(Integer.toUnsignedLong(id)).get();
        return ResponseEntity.ok(movie);
    }

    @GetMapping(path="/exists", params={"id"})
    public ResponseEntity<Boolean> isMovieAFavourite(@RequestParam int id){
        Long movieId = Integer.toUnsignedLong(id);
        Movie movie = movieRepository.findById(movieId).get();
        if (favouriteRepository.findByMovie(movie).isPresent()){
            return ResponseEntity.ok(true);
        }
        else{
            return ResponseEntity.badRequest().body(false);
        }
    }
}