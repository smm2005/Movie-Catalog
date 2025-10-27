package com.moviecatalog.catalog.controllers.rest;

import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.data.UserRepository;

@RestController
@RequestMapping(path="/api/movies", produces="application/json")
@CrossOrigin
public class MovieRESTController {

    private MovieRepository movieRepository;
    private UserRepository userRepository;

    public MovieRESTController(MovieRepository movieRepository, UserRepository userRepository){
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
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
    public ResponseEntity<Movie> getMovieById(@RequestParam("id") int id){
        Movie movie = movieRepository.findById(Integer.toUnsignedLong(id)).get();
        return ResponseEntity.ok(movie);
    }

    /*
    @PatchMapping(path="/{id}", consumes="application/json")
    public ResponseEntity<User> addMovieToLoggedUser(@PathVariable("id") int ident, @RequestBody Movie movie){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            User currentUser = (User) authentication.getPrincipal();
            currentUser.addMovie(movie);
            return ResponseEntity.ok(userRepository.save(currentUser));
        }
        else{
            return null;
        }
    }
    */
}