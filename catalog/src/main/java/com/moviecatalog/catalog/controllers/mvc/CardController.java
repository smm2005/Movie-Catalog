package com.moviecatalog.catalog.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
public class CardController {
    
    @Autowired
    public FavouriteRepository favouriteRepository;
    
    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public Recommender recommender;

    @GetMapping(params="movie")
    public String viewMovie(@RequestParam int movie, Model model){
        Movie currentMovie = movieRepository.findById(Integer.toUnsignedLong(movie)).get();
        model.addAttribute("movie", currentMovie);
        return "card";
    }

    @PostMapping(params="movie")
    public String addFavourite(@RequestParam int movie){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long movieId = Integer.toUnsignedLong(movie);

        if (authentication != null && authentication.isAuthenticated()){
            try{
                User currentUser = (User) authentication.getPrincipal();
                Favourite favourite = new Favourite();
                Movie currentMovie = movieRepository.findById(movieId).get();
                favourite.setDate(new Date());
                favourite.setUser(currentUser);
                favourite.setMovie(currentMovie);
                try{
                    log.info(currentMovie.getTitle() + " added to user: " + currentUser.getRealname());
                    favouriteRepository.save(favourite);
                }
                catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
            catch (Exception e){
                // DO NOTHING
            }
            return "redirect:/card?movie=" + Integer.toString(movie);
        }
        else{
            return "redirect:/login";
        }
    }

}
