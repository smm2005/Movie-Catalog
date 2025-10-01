package com.moviecatalog.catalog.controllers.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.data.UserRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.recommender.GenreRecommender;
import com.moviecatalog.catalog.recommender.*;
import com.moviecatalog.catalog.user.User;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    
    @Autowired
    public FavouriteRepository favouriteRepository;
    
    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public TitleRecommender recommender;

    @ModelAttribute(name="user")
    public void addUserToModel(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()){
            User currentUser = (User) authentication.getPrincipal();
            model.addAttribute("user", currentUser.getRealname());
        }
        else{
            model.addAttribute("user", "Guest");
        }
    }

    @ModelAttribute(name="recommendations")
    public void addRecommendationsToModel(Model model){
        model.addAttribute("recommendations", recommender.getRecommendations());
    }

    @ModelAttribute(name="favourites")
    public void addUserFavouritesToModel(Model model){
        model.addAttribute("favourites", favouriteRepository.findAll());
    }

    @GetMapping
    public String viewProfile(){
        return "profile";
    }

}
