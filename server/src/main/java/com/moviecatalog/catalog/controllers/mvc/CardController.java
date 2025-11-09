package com.moviecatalog.catalog.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.moviecatalog.catalog.controllers.rest.RestTemplateClass;
import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.recommender.Recommender;
import com.moviecatalog.catalog.user.User;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/card")
@CrossOrigin
public class CardController {
    
    @Autowired
    public FavouriteRepository favouriteRepository;
    
    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public RestTemplateClass restTemplate;

    @GetMapping(params="movie")
    public String viewMovie(@RequestParam int movie, Model model){
        Movie currentMovie = movieRepository.findById(Integer.toUnsignedLong(movie)).get();
        model.addAttribute("movie", currentMovie);
        return "card";
    }

    @PostMapping(params="movie")
    public String addFavourite(@RequestParam int movie){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Movie currentMovie = movieRepository.findById(Integer.toUnsignedLong(movie)).get();
        Favourite favourite = new Favourite();
        if (authentication != null && authentication.isAuthenticated()){
            User currentUser = (User) authentication.getPrincipal();
            favourite.setDate(new Date());
            favourite.setUser(currentUser);
            favourite.setMovie(currentMovie);
            try{
                log.info(currentMovie.getTitle() + " added to user: " + currentUser.getRealname());
                Favourite created = restTemplate.postForObject(String.format("http://localhost:8080/api/favourites", movie), favourite, Favourite.class);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return "redirect:/card?movie="+Integer.toString(movie);
    }

}
