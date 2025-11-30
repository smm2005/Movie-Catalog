package com.moviecatalog.catalog.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import com.moviecatalog.catalog.movie.*;

import io.jsonwebtoken.io.IOException;

public class MovieHandler {
    public ArrayList<Movie> movies = new ArrayList<Movie>();
    public GenreHandler genreHandler = new GenreHandler();

    public MovieHandler(){
    }

    public int getGenreScore(String genre){
        int score = 0;
        HashMap<String, Integer> map = genreHandler.getGenreAndCount();
        String[] genreList = genre.strip().replaceAll("\"", "").split(", ");
        for (String gen : genreList){
            score = map.get(gen);
        }
        return score;
    }

    public int getThemeScore(String[] themes, Movie mov){
        int score = 0;
        for (String theme : themes){
            score = mov.getDescription().contains(theme) ? score + 1 : score;
        }
        return score;
    }

    public ArrayList<Movie> getMoviesFromFile() {
        try{
            InputStream inputStream = getClass().getResourceAsStream("/9000plus.csv");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String currentLine;
            bufferedReader.readLine();
            while ((currentLine = bufferedReader.readLine()) != null){
                String[] data = currentLine.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
                MovieBuilder movieBuilder = new MovieBuilder(data[0], data[1]);
                String genre = data[7].replace("\"", "");
                Movie movie = movieBuilder.description(data[2])
                                          .popularity(Float.parseFloat(data[3]))
                                          .vote_count(Integer.parseInt(data[4]))
                                          .rating(Float.parseFloat(data[5]))
                                          .language(data[6])
                                          .genre(genre)
                                          .poster_url(data[8])
                                          .build();
                movies.add(movie);
            }
            bufferedReader.close();
            return movies;
        }
        catch (java.io.IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
