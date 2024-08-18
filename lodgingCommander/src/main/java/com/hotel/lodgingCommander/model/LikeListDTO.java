package com.hotel.lodgingCommander.model;

import lombok.Builder;
import lombok.Data;


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
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getHotelId() { return hotelId; }
    public void setHotelId(Long hotelId) { this.hotelId = hotelId; }

}
