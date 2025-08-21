package com.moviecatalog.catalog.handlers;

import java.io.File;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.TreeMap;

import com.moviecatalog.catalog.movie.*;

public class MovieHandler {
    public ArrayList<Movie> movies = new ArrayList<Movie>();
    public GenreHandler genreHandler = new GenreHandler();

    public MovieHandler(){
    }

    public int getGenreScore(String genre){
        int score = 0;
        TreeMap<String, Integer> map = genreHandler.getGenreAndCount();
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

    public ArrayList<Movie> getMoviesFromFile(){
        try{
            File file = new File("9000plus.csv");
            Scanner reader = new Scanner(file);
            reader.nextLine();
            while (reader.hasNextLine()){
                String[] data = reader.nextLine().split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
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
            reader.close();
            return movies;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
