package com.example.toyProject.user.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class User  {
    @JsonIgnore
    private Integer id;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
}




/*
1. start with session saving in redis
2. then create jwt 4
3. Need to play with threads
 */