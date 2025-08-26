package com.moviecatalog.catalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/favourites")
public class FavouritesController {
    
    @Autowired
    public FavouriteRepository favouriteRepository;
    
    @Autowired
    public MovieRepository movieRepository;

    @ModelAttribute(name="favourites")
    public List<Movie> getFavourites(){
        List<Movie> favourites = new ArrayList<>();
        for (Favourite favourite : favouriteRepository.findAll()){
            favourites.add(movieRepository.findById(favourite.getMovie_id()).get());
        }
        return favourites;
    }

    @GetMapping
    public String viewAllFavourites(){
        return "favourites";
    }

}
