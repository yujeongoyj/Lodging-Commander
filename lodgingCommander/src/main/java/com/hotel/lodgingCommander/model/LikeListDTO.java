package com.hotel.lodgingCommander.model;

import lombok.*;


@Data
@Builder
public class LikeListDTO {
    private long id;
    private long userId;
    private long hotelId;

    public LikeListDTO(long id, long userId, long hotelId) {
        this.id = id;
        this.userId = userId;
        this.hotelId = hotelId;
    }
}
