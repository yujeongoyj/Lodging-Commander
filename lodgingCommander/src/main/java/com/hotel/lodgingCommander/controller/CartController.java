package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.cart.CartResponseDTO;
import com.hotel.lodgingCommander.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
public class CartController {

    private CartService cartService;

    @PostMapping("/cart/{userId}")
    public ResponseEntity<Map<String, Object>> list(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        List<CartResponseDTO> cartsByUserId = cartService.getCartsByUserId(userId);

        if (cartsByUserId.isEmpty()) {
            response.put("message", "장바구니가 비어 있습니다");
            response.put("detailMessage", "숙소를 검색하고 다음 여행 계획을 세워 보세요");
        }
        response.put("cartList", cartsByUserId);
        System.out.println(cartsByUserId.get(0).getIsAvailable());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Map<String, Object> request) {
        Long id = (Long) request.get("id");
        cartService.delete(id);
        Map<String, Object> response = new HashMap<>();

        response.put("alertMessage", "장바구니에서 삭제 되었습니다.");
        return ResponseEntity.ok(response);
    }
}
