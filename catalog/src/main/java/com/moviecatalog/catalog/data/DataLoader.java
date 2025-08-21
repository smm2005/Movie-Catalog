package com.moviecatalog.catalog.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.moviecatalog.catalog.handlers.MovieHandler;
import com.moviecatalog.catalog.movie.Movie;
import com.moviecatalog.catalog.user.User;

import java.util.*;

@Component
public class DataLoader implements ApplicationRunner{
    
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception{
        MovieHandler movieHandler = new MovieHandler();
        List<Movie> movies = movieHandler.getMoviesFromFile();
        for (Movie movie : movies) {
            movieRepository.save(movie);
        }
        // userRepository.save(new User("John Doe", "jdoe@hotmail.ca", "john_doe", "1234"));
    }

}
