package com.moviecatalog.catalog.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;

@Data
public class RegistrationForm {

    private String realname;
    private String email;
    private String username;
    private String password;

    public User toUser(PasswordEncoder passwordEncoder) {
        User user = new User(realname, email, username, passwordEncoder.encode(password));
        return user;
    }

}
