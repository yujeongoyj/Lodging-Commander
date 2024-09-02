package com.hotel.lodgingCommander.model.bookingList;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingListRequestModel {
    private Long userId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalPrice;
    private int totalPeople;

    private Long cartId; // test
}

