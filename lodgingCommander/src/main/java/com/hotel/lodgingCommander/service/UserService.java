package com.hotel.lodgingCommander.service;
import com.hotel.lodgingCommander.dto.user.UserDTO;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.entity.enums.UserGrade;
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

    private final int SILVER_THRESHOLD = 5;
    private final int GOLD_THRESHOLD = 15;
    private final int VIP_THRESHOLD = 30;

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

    public UserGrade calculateUserGrade(int stayCount) {
        if (stayCount >= VIP_THRESHOLD) {
            return UserGrade.VIP;
        } else if (stayCount >= GOLD_THRESHOLD) {
            return UserGrade.GOLD;
        } else if (stayCount >= SILVER_THRESHOLD) {
            return UserGrade.SILVER;
        } else {
            return UserGrade.SILVER;
        }
    }

    public int calculateRemainingPoints(int stayCount) {
        if (stayCount < SILVER_THRESHOLD) {
            return SILVER_THRESHOLD - stayCount;
        } else if (stayCount < GOLD_THRESHOLD) {
            return GOLD_THRESHOLD - stayCount;
        } else if (stayCount < VIP_THRESHOLD) {
            return VIP_THRESHOLD - stayCount;
        } else {
            return 0;
        }
    }

    public void updateUserStayCount(String email) {
        Optional<User> userOptional = USER_REPOSITORY.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            //  user.setStayCount(user.getStayCount() + 1);
            //  UserGrade newGrade = calculateUserGrade(user.getStayCount());
            //  user.setGrade(String.valueOf(newGrade));

            USER_REPOSITORY.save(user);
        } else {
            throw new RuntimeException("User not found with email: " + email);
        }
    }


    public User getUserById(Long userId) {
        return USER_REPOSITORY.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
    }
}
