package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.UserDTO;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository USER_REPOSITORY;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        USER_REPOSITORY = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        USER_REPOSITORY.save(user);
    }

    public Optional<User> getUserInfo(String email) {
        return USER_REPOSITORY.findByEmail(email);
    }

    public void update(String email, User user) {
        Optional<User> existingUserOpt = USER_REPOSITORY.findByEmail(email);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if (user.getTel() != null && !user.getTel().isEmpty()) {
                existingUser.setTel(user.getTel());
            }
            if (user.getNickname() != null && !user.getNickname().isEmpty()) {
                existingUser.setNickname(user.getNickname());
            }
            USER_REPOSITORY.save(existingUser);
        }
    }

    @Transactional
    public void delete(String email) {
        USER_REPOSITORY.deleteByEmail(email);
    }

    public UserDTO getUserDTO(String email) {
        Optional<User> user = USER_REPOSITORY.findByEmail(email);
        return new UserDTO(user.orElse(null));
    }



}
