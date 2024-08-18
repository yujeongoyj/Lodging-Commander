package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.user.CustomUserDetails;
import com.hotel.lodgingCommander.dto.user.UserDTO;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository USER_REPOSITORY;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.USER_REPOSITORY = userRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> byEmail = USER_REPOSITORY.findByEmail(email);
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(byEmail.get().getEmail());
        userDTO.setPassword(byEmail.get().getPassword());
        userDTO.setRole(byEmail.get().getRole());

        return new CustomUserDetails(userDTO);
    }
}