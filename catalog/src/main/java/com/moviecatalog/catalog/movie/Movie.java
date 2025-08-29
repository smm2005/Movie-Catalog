package com.moviecatalog.catalog.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.moviecatalog.catalog.user.User;

/*
JDBC
-----
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
*/

// JPA
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

@Entity
@Table(name="MOVIES")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name = "title")
    private String title;

    @Column(name = "overview", length = 1000)
    private String description;
    
    @Column(name = "popularity")
    private float popularity;
    
    @Column(name = "vote_count")
    private int vote_count;

    @Column(name = "rating")
    private float rating;
    
    @Column(name = "language")
    private String language;

    @Column(name = "genre")
    private String genre;

    @Column(name = "poster_url")
    private String poster_url;

    @OneToOne(targetEntity=Favourite.class, mappedBy="movie")
    @JsonIgnore
    private Favourite favourite;

    public String toString(){
        return "MOVIE INFO:\t"+this.title;
    }

    public boolean isNew(){
        return this.id == null;
    }

    public String getCompressedJPG(){
        File inputImage = new File(this.poster_url);
        File outputImage = new File("src/main/resources/static/images/movies"+this.id+".jpg");
        try{
            Thumbnails.of(inputImage)
                .scale(1)
                .outputQuality(0.5)
                .outputFormat("jpg")
                .toFile(outputImage);
            return outputImage.getName();
        }
        catch (IOException e){
            return this.poster_url;
        }
    }
}
