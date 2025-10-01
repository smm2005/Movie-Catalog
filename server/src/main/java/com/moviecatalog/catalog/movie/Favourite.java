package com.moviecatalog.catalog.movie;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.moviecatalog.catalog.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="favourites")
@Data
@Table(name="favourites")
@NoArgsConstructor
@AllArgsConstructor
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="favourite_id")
    private Long id;

    @Column(name="created")
    private Date date;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    @JsonBackReference
    private User user;

    @OneToOne
    @JoinColumn(name="movie_id", referencedColumnName = "movie_id")
    private Movie movie;
    
}
