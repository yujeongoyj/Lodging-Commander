package com.hotel.lodgingCommander.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDTO {
    private Long id;
    private String name;
    private int price;
    private Long hotelId;
    private Long imgId;
    private int quantity;
    private String detail;
    private int maxPeople;
}
