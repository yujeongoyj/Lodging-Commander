package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.cart.CartRequestModel;
import com.hotel.lodgingCommander.model.cart.CartResponseModel;
import com.hotel.lodgingCommander.model.entity.Cart;
import com.hotel.lodgingCommander.model.repository.CartRepository;
import com.hotel.lodgingCommander.model.repository.ReviewRepository;
import com.hotel.lodgingCommander.model.repository.RoomRepository;
import com.hotel.lodgingCommander.model.repository.UserRepository;
import com.hotel.lodgingCommander.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public Map<?, ?> getCartsByUserId(Long userId) {
        var carts = cartRepository.findCartWithAvailabilityByUserId(userId);
        var response = new HashMap<>();
        response.put("cartList", carts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        return response;
    }

    private CartResponseModel convertToDTO(Object[] result) {
        CartResponseModel dto = new CartResponseModel();

        dto.setId(((Number) result[0]).longValue()); // c.id
        dto.setHotelName((String) result[1]);        // h.name AS hotelName
        dto.setRoomName((String) result[2]);         // r.name AS roomName
        dto.setCheckInDate(((java.sql.Date) result[3]).toLocalDate()); // c.checkin_date AS checkInDate
        dto.setCheckOutDate(((java.sql.Date) result[4]).toLocalDate()); // c.checkout_date AS checkOutDate
        dto.setPrice(((Number) result[5]).intValue()); // r.price
        dto.setImgPath((String) result[6]);           // i.path AS imgPath
        dto.setHotelId(((Number) result[7]).longValue()); // h.id AS hotelId
        dto.setGrade(((Number) result[8]).intValue()); // h.grade
        dto.setUserGrade((String) result[9]);         // u.grade AS userGrade
        dto.setIsAvailable(((Number) result[10]).intValue() == 1); // isAvailable

        dto.setRoomId(((Number) result[11]).longValue()); // r.id AS roomId

        dto.setReviewCount(reviewRepository.countByHotel_Id(dto.getHotelId()));
        return dto;
    }

    @Transactional
    public Boolean delete(Map<?, ?> request) {
        var response = new HashMap<>();
        try {
            var idNumber = (Number) request.get("id");
            if (idNumber != null) {
                Long id = idNumber.longValue();
                cartRepository.deleteById(id);
                return true;
            } else {
               return false;
            }
        } catch (Exception e) {
            // 예외 처리
            return false;
        }
    }

    @Transactional
    public Boolean insert(CartRequestModel requestDTO) {
        var user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        var room = roomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room Not Found"));
        var cartEntity = Cart.builder()
                .id(requestDTO.getId())
                .checkInDate(requestDTO.getCheckInDate())
                .checkOutDate(requestDTO.getCheckOutDate())
                .room(room)
                .user(user)
                .build();
        return cartRepository.save(cartEntity).getId() == null ? false : true;
    }
}
