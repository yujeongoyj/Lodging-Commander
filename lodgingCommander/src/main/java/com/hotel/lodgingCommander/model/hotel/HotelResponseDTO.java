package com.hotel.lodgingCommander.model.hotel;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HotelResponseDTO {
    private Long id;
    private String hotelName;
    private int grade;
    private String detail;

    private List<String> imgPath;

    private int minPrice;

    private int reviewCount;

    private String category;

    private Map<String, Boolean> facilities;
}
