package com.moviecatalog.catalog.user;

import org.springframework.data.domain.Persistable;
import org.springframework.security.core.userdetails.UserDetails;

import com.moviecatalog.catalog.movie.Movie;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.moviecatalog.catalog.movie.Favourite;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor(access=AccessLevel.PRIVATE, force=true)
@RequiredArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Persistable<Long>{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id", nullable=false, updatable=false)
    @JsonProperty("user_id")
    private Long id;

    @NotNull
    @Column(name="realname", nullable=false, updatable=false)
    @Size(min=1, max=130, message="Name is too short or too long")
    private final String realname;

    @NotNull
    @Email(message="Invalid email")
    @Size(min=5, max=100, message="Email is too short or too long")
    @Column(name="email")
    private final String email;

    @NotNull
    @Column(name="username")
    @Size(min=1, max=50, message="Username must be at least 1 character long and at most 50 characters long")
    private final String username;

    @NotNull
    @Column(name="password")
    @Size(min=8, message="Password must be at least 8 characters long")
    private final String password;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
    @JsonManagedReference
    private Set<Favourite> favourites = new HashSet<Favourite>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    @Override
    public boolean isAccountNonLocked(){
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    @Override
    public boolean isEnabled(){
        return true;
    }

    public boolean isNew(){
        return this.id == null;
    }

    public String getPassword(){
        return this.password;
    }
}
