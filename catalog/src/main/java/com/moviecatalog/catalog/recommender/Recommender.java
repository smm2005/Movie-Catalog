package com.moviecatalog.catalog.recommender;

import java.util.List;
import com.moviecatalog.catalog.movie.Movie;


public interface Recommender {

    List<Movie> getMovies();
    Float getMetricAverage();
    List<Movie> getRecommendations();

}
