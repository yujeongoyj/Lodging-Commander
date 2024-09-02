package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.hotel.HotelRequestModel;
import com.hotel.lodgingCommander.model.hotel.HotelResponseModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface HotelService {

    HotelResponseModel getHotelById(Long id);


    List<?>  findAvailableHotels(String location, LocalDate checkInDate, LocalDate checkOutDate, int guests, int rooms);

    Map<?, ?> getAddressByHotelId(Long hotelId);

    List<?> findByLocation(String location);

    List<?> getRecentHotels();

    Boolean saveHotel(HotelRequestModel hotelDTO);

    List<?> getHotelsByUserId(Long userId);

    Map<?, ?> getHotelNamesByIds(List<Long> ids);
}
