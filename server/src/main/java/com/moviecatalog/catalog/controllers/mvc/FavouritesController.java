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
import com.moviecatalog.catalog.recommender.Recommender;
import com.moviecatalog.catalog.user.User;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/favourites")
public class FavouritesController {
    
    @Autowired
    public FavouriteRepository favouriteRepository;
    
    @Autowired
    public MovieRepository movieRepository;

    @Autowired
    public UserRepository userRepository;

    @ModelAttribute(name="favourites")
    public void addUserFavouritesToModel(Model model){
        model.addAttribute("favourites", favouriteRepository.findAll());
    }

    @GetMapping
    public String viewAllFavourites(){
        return "favourites";
    }

}
