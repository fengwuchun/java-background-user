package com.demo.backend.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

import lombok.Data;

@Data
public class LoginUser implements UserDetails{
    
       private Long id;

    private String email;

    private String role;

      public LoginUser(
            Long id,
            String email,
            String role
    ){

        this.id = id;
        this.email = email;
        this.role = role;

    }

       @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(
            new SimpleGrantedAuthority(role)
        );

    }

     @Override
    public String getUsername() {
        return email;
    }



    @Override
    public String getPassword() {
        return "";
    }

}
