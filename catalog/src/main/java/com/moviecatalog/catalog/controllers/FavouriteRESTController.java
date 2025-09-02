package com.moviecatalog.catalog.controllers;

import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.data.UserRepository;

@RestController
@RequestMapping(path="/api/favourites", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class FavouriteRESTController {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public Iterable<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }
    
    @GetMapping(params="page")
    public Iterable<Favourite> getFavouritesGivenPageNumber(@RequestParam int page){
        return favouriteRepository.findAll(PageRequest.of(page, 30)).getContent();
    }

    @GetMapping(params="user")
    public List<Favourite> getFavouritesGivenUser(@RequestParam int user){
        return favouriteRepository.findAllByUser(userRepository.getReferenceById(Integer.toUnsignedLong(user)));
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