package com.hotel.lodgingCommander.model.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO {
    private Long id;
    private String roomName;
    private int maxPeople;
    private int price;
    private String detail;
    private boolean reservable;
    private String imgPath;

    private String hotelName;
}
