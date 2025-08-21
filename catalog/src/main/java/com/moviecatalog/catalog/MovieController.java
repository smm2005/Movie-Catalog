package com.moviecatalog.catalog;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.moviecatalog.catalog.data.MovieRepository;
import com.moviecatalog.catalog.handlers.MovieHandler;
import com.moviecatalog.catalog.movie.Movie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Controller
@RequestMapping("/movies")
@SessionAttributes("catalog")
public class MovieController {

    @Autowired
    MovieRepository movieRepo;

    @ModelAttribute(name="movies")
    public void addMoviesToModel(Model model){
        Iterable<Movie> movies = movieRepo.findAll();
        model.addAttribute("movies", movies);
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
    
    
}
