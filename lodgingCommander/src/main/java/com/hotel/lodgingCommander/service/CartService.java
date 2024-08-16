package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.cart.CartResponseDTO;
import com.hotel.lodgingCommander.repository.CartRepository;
import com.hotel.lodgingCommander.repository.ReviewRepository;
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

    @Transactional(readOnly = true)
    public List<CartResponseDTO> getCartsByUserId(Long userId) {
        List<Object[]> carts = cartRepository.findCartWithAvailabilityByUserId(userId);
        System.out.println("roomId:"+carts.get(0)[11]);;
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
}
