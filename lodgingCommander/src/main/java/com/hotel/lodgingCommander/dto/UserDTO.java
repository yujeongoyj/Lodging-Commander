package com.hotel.lodgingCommander.dto;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO implements UserDetails {
    private int id;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String role;
    private String grade;
    private List<GrantedAuthority> authorities;

    @Override
    public List<GrantedAuthority> getAuthorities() {
        authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }
}
