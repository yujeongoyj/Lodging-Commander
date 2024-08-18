package com.hotel.lodgingCommander.dto.bookingList;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingListRequestDTO {
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalPrice;
    private int totalPeople;

    private Long cartId; // test
}
