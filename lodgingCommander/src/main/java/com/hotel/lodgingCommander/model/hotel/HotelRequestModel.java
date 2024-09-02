package com.hotel.lodgingCommander.model.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelRequestModel {
    private Long id;
    private String name;
    private Long userId;
    private Long addressId;
    private Long categoryId;
    private String tel;
    private int grade;
    private String detail;


}
