package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.cart.CartRequestDTO;
import com.hotel.lodgingCommander.dto.cart.CartResponseDTO;
import com.hotel.lodgingCommander.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
public class CartController {

    private CartService CART_SERVICE;

    @GetMapping("/cart/{userId}")
    public ResponseEntity<Map<String, Object>> list(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        List<CartResponseDTO> cartsByUserId = CART_SERVICE.getCartsByUserId(userId);

        response.put("cartList", cartsByUserId);
        System.out.println(cartsByUserId.get(0).getIsAvailable());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Map<String, Object> request) {
        Long id = (Long) request.get("id");
        CART_SERVICE.delete(id);
        Map<String, Object> response = new HashMap<>();

        response.put("alertMessage", "장바구니에서 삭제 되었습니다.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/add/{id}")
    public void addCart(@PathVariable Long id, @RequestBody CartRequestDTO requestDTO) {
        try {
            CART_SERVICE.insert(requestDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
