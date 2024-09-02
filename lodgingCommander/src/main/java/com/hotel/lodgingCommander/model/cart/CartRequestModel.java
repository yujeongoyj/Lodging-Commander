package com.hotel.lodgingCommander.model.cart;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CartRequestModel {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private Long userId;
}
