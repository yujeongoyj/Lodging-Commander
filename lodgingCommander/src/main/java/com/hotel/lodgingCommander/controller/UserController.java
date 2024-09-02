package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.user.UserModel;
import com.hotel.lodgingCommander.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl service;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody UserModel userModel) {
        return ResponseEntity.ok(service.registerUser(userModel.toEntity()));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UserModel userModel) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(service.update(authentication.getName(), userModel.toEntity()));
    }

    @GetMapping("/nickName/{id}")
    public ResponseEntity<?> getUserNicknameById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getUserById(id).getNickname());
    }


    @DeleteMapping
    public ResponseEntity<?> delete() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(service.delete(authentication.getName()));
    }

    @RequestMapping("/authSuccess")
    public ResponseEntity<?> authSuccess() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(service.getUserModel(authentication));
    }

    @RequestMapping("/authFail")
    public ResponseEntity<?> authFail() {
        return ResponseEntity.ok(false);
    }

    @RequestMapping("/logOutSuccess")
    public ResponseEntity<?> logOutSuccess() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/checkEmail")
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> emailRequest) {
        return ResponseEntity.ok(service.emailExists(emailRequest.get("email")));
    }

}