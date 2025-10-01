package com.moviecatalog.catalog.controllers.mvc;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

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
@CrossOrigin
public class MovieController {

    @Autowired
    MovieRepository movieRepo;

    @Autowired
    FavouriteRepository favouriteRepository;

    public boolean search_mode = false;
    public int page_count = -1;

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

    @ModelAttribute(name="search_mode")
    public void addSearchToModel(Model model){
        model.addAttribute("search_mode", search_mode);
    }

    @GetMapping(params = "page")
    public String showMoviesPerPage(@RequestParam(defaultValue="0") int page, Model model){
        Iterable<Movie> movies = movieRepo.findAll(PageRequest.of(page, 30));
        search_mode = false;
        page_count = movieRepo.findAll().size() / 30;
        model.addAttribute("page", movies);
        model.addAttribute("pageNum", page);
        return "movies";
    }

    @GetMapping(params={"page", "search"})
    public String getSearchedMovies(@RequestParam(defaultValue="0") int page, @RequestParam String search, Model model) {
        if (search != "" || search != null){
            List<Movie> movies = movieRepo.findByTitleContainingIgnoreCase(search, PageRequest.of(page, 30)).getContent();
            page_count = movies.size() / 30;
            search_mode = true;
            model.addAttribute("page", movies);
            model.addAttribute("pageNum", 0);
            model.addAttribute("pageCount", page_count);
            model.addAttribute("search", search);
            return "movies";
        }
        else{
            search_mode = false;
            return "redirect:/movies?page="+Integer.toString(page);
        }
    }

    @PostMapping(params="page")
    public String getMoviesGivenPageNumber(@RequestParam int page) {
        if (page <= 0){
            return "redirect:/movies?page=0";
        }
        else if (page > 327){
            return "redirect:/movies?page=327";
        }
        return "redirect:/movies?page=" + Integer.toString(page);
    }

    @PostMapping(params={"page", "search"})
    public String getSearchedMoviesGivenPageNumber(@RequestParam int page, @RequestParam String search) {
        if (search == "" || search == null){
            return getMoviesGivenPageNumber(page);
        }
        if (page <= 0){
            return "redirect:/movies?page=0&search="+search;
        }
        else if (page > page_count){
            return "redirect:/movies?page=" + Integer.toString(page_count) + "&search=" + search;
        }
        return "redirect:/movies?page=" + Integer.toString(page) + "&search=" +search;
    }

    @PostMapping(params={"page", "movie"})
    public String addMovieToUser(@RequestParam int page, @RequestParam int movie){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long movieId = Integer.toUnsignedLong(movie);

        if (authentication != null && authentication.isAuthenticated()){
            try{
                User currentUser = (User) authentication.getPrincipal();
                Favourite favourite = new Favourite();
                Movie currentMovie = movieRepo.findById(movieId).get();
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
            return "redirect:/movies?page=" + Integer.toString(page);
        }
        else{
            return "redirect:/login";
        }
    }
    
}
