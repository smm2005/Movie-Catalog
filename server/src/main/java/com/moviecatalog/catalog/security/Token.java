package com.moviecatalog.catalog.security;

import java.util.Date;

import org.springframework.data.domain.Persistable;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.*;

@Entity(name="tokens")
@Table(name="tokens")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Persistable<Long> {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="token_id", nullable=false, updatable=false)
    private Long id;

    @Column(name="token", nullable=false, length=1000)
    private String token;

    @Column(name="expiry", nullable=false)
    private Date expiry;

    @Column(name="userId", nullable=false)
    private Long userId;

    @Column(name="revoked", nullable=false)
    private int revoked;

    public boolean isNew(){
        return this.id == null;
    }

    public boolean isRevoked(){
        return revoked == 1;
    }

}
