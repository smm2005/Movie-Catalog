package com.moviecatalog.catalog;

import com.moviecatalog.catalog.movie.Movie;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviecatalog.catalog.data.MovieRepository;

@RestController
@RequestMapping(path="/api/movies", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class MovieRESTController {
    private MovieRepository movieRepository;

    public MovieRESTController(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @GetMapping(params="page")
    public Iterable<Movie> getMoviesGivenPageNumber(@RequestParam int page){
        return movieRepository.findAll(PageRequest.of(page, 30)).getContent();
    }
    
}