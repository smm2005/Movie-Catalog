package com.moviecatalog.catalog.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.moviecatalog.catalog.data.FavouriteRepository;
import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.movie.Favourite;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;


@Slf4j
@Controller
@RequestMapping("/movies")
@SessionAttributes("catalog")
public class MovieController {

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    FavouriteRepository favouriteRepository;

    @ModelAttribute(name="movies")
    public void addMoviesToModel(Model model){
        Iterable<Movie> movies = movieRepo.findAll();
        model.addAttribute("movies", movies);
    }

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

    @GetMapping
    public String showMovies(){
        return "movies";
    }

    @GetMapping(params = "page")
    public String showMoviesPerPage(@RequestParam(defaultValue="0") int page, Model model){
        Iterable<Movie> movies = movieRepo.findAll(PageRequest.of(page, 30));
        model.addAttribute("page", movies);
        model.addAttribute("pageNum", page);
        return "movies";
    }

    @PostMapping
    public String getMoviesGivenPageNumber(@RequestParam int page) {
        if (page <= 0){
            return "redirect:/movies?page=0";
        }
        else if (page > 327){
            return "redirect:/movies?page=327";
        }
        return "redirect:/movies?page=" + Integer.toString(page);
    }

    @PostMapping(params="movie")
    public String addMovieToUser(@RequestParam int page, @RequestParam int movie){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Favourite favourite = new Favourite();
        Long movieId = Integer.toUnsignedLong(movie);

        if (authentication != null && authentication.isAuthenticated()){
            User currentUser = (User) authentication.getPrincipal();
            favourite.setDate(new Date());
            favourite.setUser(currentUser);
            favourite.setMovie(movieRepo.findById(movieId).get());
            favouriteRepository.save(favourite);
            log.info(movieRepo.findById(movieId).get().getTitle() + " added to user: " + currentUser.getRealname());
            return "redirect:/movies?page=" + Integer.toString(page);
        }
        else{
            return "redirect:/login";
        }
    }
    
    
}
