package com.moviecatalog.catalog.handlers;

import java.util.TreeMap;

public class GenreHandler {
    public TreeMap<String, Integer> genreAndCount;
    public String[] genres = {"Action", "Adventure", "Animation", "Biography", "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "Film-Noir", "History", "Horror", "Music", "Musical", "Mystery", "Romance", "Science Fiction", "Short", "Sport", "Thriller", "TV Movie", "War", "Western"};

    public GenreHandler(){
        genreAndCount = new TreeMap<String, Integer>();
    }

    public TreeMap<String, Integer> getGenreAndCount(){
        for (int i = 0; i < genres.length; i++){
            genreAndCount.put(genres[i], i+1);
        }
        return this.genreAndCount;
    }
}
