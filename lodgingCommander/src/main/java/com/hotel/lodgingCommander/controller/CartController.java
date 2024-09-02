package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.cart.CartRequestModel;
import com.hotel.lodgingCommander.model.cart.CartResponseModel;
import com.hotel.lodgingCommander.service.CartService;
import com.hotel.lodgingCommander.service.impl.CartServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartServiceImpl service;

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getCartsByUserId(userId));
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestBody Map<?,?> request) {
        return ResponseEntity.ok(service.delete(request));
    }

    @PostMapping
    public ResponseEntity<?> addCart( @RequestBody CartRequestModel requestDTO) {
        return ResponseEntity.ok(service.insert(requestDTO));
    }
}
