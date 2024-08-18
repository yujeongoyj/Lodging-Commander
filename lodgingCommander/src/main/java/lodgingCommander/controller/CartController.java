package lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.cart.CartRequestDTO;
import com.hotel.lodgingCommander.dto.cart.CartResponseDTO;
import com.hotel.lodgingCommander.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Number idNumber = (Number) request.get("id");
            if (idNumber != null) {
                Long id = idNumber.longValue();
                CART_SERVICE.delete(id);
                response.put("alertMessage", "장바구니에서 삭제 되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                // id 값이 없거나 null인 경우
                response.put("alertMessage", "유효하지 않은 ID입니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // 예외 처리
            response.put("alertMessage", "삭제 과정에서 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
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
