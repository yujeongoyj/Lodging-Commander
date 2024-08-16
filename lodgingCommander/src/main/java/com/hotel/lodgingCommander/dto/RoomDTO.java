package com.hotel.lodgingCommander.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
