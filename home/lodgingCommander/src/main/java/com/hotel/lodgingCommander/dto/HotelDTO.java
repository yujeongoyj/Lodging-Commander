package com.hotel.lodgingCommander.dto;

import com.hotel.lodgingCommander.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {
    private Long id;
    private String name;
    private Long userId;
    private Long addressId;
    private Long categoryId;
    private String tel;
    private int grade;
    private String detail;


}
