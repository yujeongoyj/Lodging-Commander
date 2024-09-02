package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.entity.User;
import com.hotel.lodgingCommander.model.repository.UserRepository;
import com.hotel.lodgingCommander.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Boolean registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user).getId() == null ? false : true;
    }

    public Boolean update(String email, User user) {
        try {
            User existingUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            Optional.ofNullable(user.getPassword())
                    .filter(password -> !password.isEmpty())
                    .ifPresent(password -> existingUser.setPassword(passwordEncoder.encode(password)));

            Optional.ofNullable(user.getTel())
                    .filter(tel -> !tel.isEmpty())
                    .ifPresent(existingUser::setTel);

            Optional.ofNullable(user.getNickname())
                    .filter(nickname -> !nickname.isEmpty())
                    .ifPresent(existingUser::setNickname);

            userRepository.save(existingUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    @Transactional
    public Boolean delete(String email) {
        userRepository.deleteByEmail(email);
        SecurityContextHolder.clearContext();
        return userRepository.findByEmail(email) == null ? false : true;
    }


    public Map<?, ?> getUserModel(Authentication authentication) {
        var user = userRepository.findByEmail(authentication.getName()).get();
        return new HashMap<>() {
            {
                put("result", "success");
                put("userDTO", user);
                put("id", user.getId());
                put("email", user.getEmail());
                put("password", user.getPassword());
                put("nickname", user.getNickname());
                put("tel", user.getTel());
                put("grade", user.getGrade());
                put("role", user.getRole());
            }
        };
    }


    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }

    public Boolean emailExists(String email) {
        return userRepository.existsByEmail(email) ? true : false;
    }


}
