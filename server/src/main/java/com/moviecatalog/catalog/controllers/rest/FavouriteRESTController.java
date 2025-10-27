package com.moviecatalog.catalog.controllers.rest;

import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.data.UserRepository;

@Slf4j
@RestController
@RequestMapping(path="/api/favourites", produces="application/json")
@CrossOrigin
public class FavouriteRESTController {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserController userController;

    @Autowired
    private MovieRESTController movieRESTController;

    RestTemplate rest = new RestTemplate();

    @GetMapping
    public Iterable<Favourite> getAllFavourites() {
        return favouriteRepository.findAll();
    }
    
    @GetMapping(params="movie")
    public Favourite getFavouriteGivenMovie(@RequestParam int movie){
        Long movieId = Integer.toUnsignedLong(movie);
        Movie currentMovie = movieRepository.findById(movieId).get();
        return favouriteRepository.findByMovie(currentMovie);
    }

    @GetMapping(params="page")
    public Iterable<Favourite> getFavouritesGivenPageNumber(@RequestParam int page){
        return favouriteRepository.findAll(PageRequest.of(page, 30)).getContent();
    }

    @GetMapping(params="user")
    public List<Favourite> getFavouritesGivenUser(@RequestParam int user){
        return favouriteRepository.findAllByUser(userRepository.getReferenceById(Integer.toUnsignedLong(user)));
    }

    @PostMapping(params="id")
    public ResponseEntity<Favourite> addMovieToFavourites(@RequestParam("id") int movieId, @RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken){
        Favourite favourite = new Favourite();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        HttpEntity<User> userEntity = new HttpEntity<>(headers);
        HttpEntity<Movie> movieEntity = new HttpEntity<>(headers);

        User currentUser = rest.exchange("http://localhost:8080/api/user", HttpMethod.GET, userEntity, User.class).getBody();
        Movie movie = rest.exchange("http://localhost:8080/api/movies?id={id}", HttpMethod.GET, movieEntity, Movie.class, movieId).getBody();

        favourite.setDate(new Date());
        favourite.setMovie(movie);
        favourite.setUser(currentUser);
        return ResponseEntity.ok(favourite);
    }

}