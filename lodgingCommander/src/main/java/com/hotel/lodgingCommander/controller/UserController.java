package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.UserDTO;
import com.hotel.lodgingCommander.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("authSuccess")
    public ResponseEntity<Map<String, Object>> authSuccess(Authentication authentication) {
        return userService.handleAuthSuccess(authentication);
    }

    @RequestMapping("authFail")
    public ResponseEntity<Map<String, Object>> authFail() {
        return userService.handleAuthFail();
    }

    @RequestMapping("logOutSuccess")
    public ResponseEntity<Void> logOutSuccess(Authentication authentication) {
        return userService.handleLogOutSuccess(authentication);
    }

    @GetMapping("logInClear")
    public String logInClear() {
        return "logInClear";
    }

    @PostMapping("register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserDTO userDTO) {
        userService.register(userDTO);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return ResponseEntity.ok(resultMap);


    }
}
