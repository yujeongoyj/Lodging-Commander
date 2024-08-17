package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.cart.CartRequestDTO;
import com.hotel.lodgingCommander.dto.cart.CartResponseDTO;
import com.hotel.lodgingCommander.entity.Cart;
import com.hotel.lodgingCommander.entity.Room;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.repository.CartRepository;
import com.hotel.lodgingCommander.repository.ReviewRepository;
import com.hotel.lodgingCommander.repository.RoomRepository;
import com.hotel.lodgingCommander.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private ReviewRepository reviewRepository;

    private RoomRepository roomRepository;
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<CartResponseDTO> getCartsByUserId(Long userId) {
        List<Object[]> carts = cartRepository.findCartWithAvailabilityByUserId(userId);
        return carts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private CartResponseDTO convertToDTO(Object[] result) {
        CartResponseDTO dto = new CartResponseDTO();

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
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

    @Transactional
    public void insert(CartRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        Room room = roomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room Not Found"));
        Cart cartEntity = Cart.builder()
                .id(requestDTO.getId())
                .checkInDate(requestDTO.getCheckInDate())
                .checkOutDate(requestDTO.getCheckOutDate())
                .room(room)
                .user(user)
                .build();
        cartRepository.save(cartEntity);
        cartRepository.deleteById(requestDTO.getId());
    }
}
