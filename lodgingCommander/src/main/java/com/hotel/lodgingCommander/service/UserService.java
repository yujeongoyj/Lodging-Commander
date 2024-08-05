package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.UserDTO;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.entity.enums.UserGrade;
import com.hotel.lodgingCommander.entity.enums.UserRole;
import com.hotel.lodgingCommander.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public ResponseEntity<Map<String, Object>> handleAuthSuccess(Authentication authentication) {
        Map<String, Object> resultMap = new HashMap<>();
        User user = (User) authentication.getPrincipal();

        resultMap.put("result", "success");
        resultMap.put("id", user.getId());
        resultMap.put("email", "test01@naver.com");
        resultMap.put("tel", "010-1234-5678");
        resultMap.put("grade", UserGrade.SILVER.getCode());
        resultMap.put("role", UserRole.USER.getCode());

        return ResponseEntity.ok(resultMap);
    }

    public ResponseEntity<Map<String, Object>> handleAuthFail() {
        System.out.println("Auth has failed");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "fail");

        return ResponseEntity.ok(resultMap);
    }

    public ResponseEntity<Void> handleLogOutSuccess(Authentication authentication) {
        System.out.println("log out success");
        System.out.println(authentication);

        return ResponseEntity.ok().build();
    }

    public void register(UserDTO userDTO) {
        User user = new User();
//        user.setUsername(userDTO.getUsername());
//        user.setPassword(encoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail());
        user.setTel(userDTO.getTel());
        user.setRole(userDTO.getRole());
        user.setGrade(userDTO.getGrade());

        userRepository.save(user);
    }
}
