package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.user.CustomUserDetails;
import com.hotel.lodgingCommander.model.user.UserModel;
import com.hotel.lodgingCommander.model.entity.User;
import com.hotel.lodgingCommander.model.repository.UserRepository;
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
        UserModel userModel = new UserModel();
        userModel.setEmail(byEmail.get().getEmail());
        userModel.setPassword(byEmail.get().getPassword());
        userModel.setRole(byEmail.get().getRole());

        return new CustomUserDetails(userModel);
    }
}