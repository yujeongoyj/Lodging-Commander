package com.hotel.lodgingCommander.dto.cart;

import com.hotel.lodgingCommander.entity.Cart;
import com.hotel.lodgingCommander.entity.enums.UserGrade;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CartResponseDTO {
    private Long id;
    private String hotelName;
    private String roomName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int price;
    private String imgPath;
    private Long hotelId;
    private int grade;
    private String userGrade;
    private Boolean isAvailable;
    private int reviewCount;
    private Long roomId;
}
