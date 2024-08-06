/*
package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.entity.Category;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.User;

public class HotelMapper {

    public static Hotel toEntity(HotelDTO dto) {
        return Hotel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .user(User.builder().id(dto.getUserId()).build())
                .address(Address.builder().id(dto.getAddressId()).build())
                .category(Category.builder().id(dto.getCategoryId()).build())
                .tel(dto.getTel())
                .grade(dto.getGrade())
                .detail(dto.getDetail())
                .build();
    }

    public static HotelDTO toDTO(Hotel hotel) {
        return HotelDTO.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .userId(hotel.getUser().getId())
                .addressId(hotel.getAddress().getId())
                .categoryId(hotel.getCategory().getId())
                .tel(hotel.getTel())
                .grade(hotel.getGrade())
                .detail(hotel.getDetail())
                .build();
    }
}
*/
