package com.hotel.lodgingCommander.model.room;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomRequestModel {
    private Long id;
    private String name;
    private int price;
    private String detail;
    private int maxPeople;
    private Long hotelId;
    private Integer quantity;
    private Long imgId;
}
