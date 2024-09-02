package com.hotel.lodgingCommander.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponseModel {
    private Long id;
    private String roomName;
    private int maxPeople;
    private Integer price;
    private String detail;
    private boolean reservable;
    private String imgPath;
    private String hotelName;
    private Long hotelId;
}
