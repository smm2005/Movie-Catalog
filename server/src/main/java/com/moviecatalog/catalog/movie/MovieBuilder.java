package com.moviecatalog.catalog.movie;

import com.moviecatalog.catalog.Builder;

import lombok.Data;

@Data
public class MovieBuilder implements Builder{

    private Movie movie = new Movie();

    public MovieBuilder(String releaseDate, String title){
        movie.setReleaseDate(releaseDate);
        movie.setTitle(title);
    }

    public MovieBuilder description(String description){
        movie.setDescription(description);
        return this;
    }

    @Override
    public MovieBuilder popularity(float popularity){
        movie.setPopularity(popularity);
        return this;
    }

    @Override
    public MovieBuilder vote_count(int vote_count){
        movie.setVote_count(vote_count);
        return this;
    }

    @Override
    public MovieBuilder rating(float rating){
        movie.setRating(rating);
        return this;
    }

    @Override
    public MovieBuilder language(String language){
        movie.setLanguage(language);
        return this;
    }

    @Override
    public MovieBuilder genre(String genre){
        movie.setGenre(genre);
        return this;
    }

    public MovieBuilder poster_url(String poster_url){
        movie.setPoster_url(poster_url);
        return this;
    }

    @Override
    public Movie build(){
        return movie;
    }

}   
