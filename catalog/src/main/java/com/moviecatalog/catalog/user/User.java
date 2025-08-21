package com.moviecatalog.catalog.user;

import org.springframework.data.domain.Persistable;
import org.springframework.security.core.userdetails.UserDetails;

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
public class User implements UserDetails, Persistable<Long>{
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable=false, updatable=false)
    private Long id;

    @NotNull
    @Column(name="realname", nullable=false, updatable=false)
    private final String realname;

    @NotNull
    @Email(message="Invalid email")
    @Column(name="email")
    private final String email;

    @NotNull
    @Column(name="username")
    private final String username;

    @NotNull
    @Size(min=8, message="Password must be at least 8 characters long")
    @Column(name="pass")
    private final String password;

    @Override
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

}
