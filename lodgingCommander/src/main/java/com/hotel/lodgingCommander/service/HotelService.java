package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.repository.HotelRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private final HotelRepository HOTEL_REPOSITORY;

    public HotelService(HotelRepository hotelRepository) {
        HOTEL_REPOSITORY = hotelRepository;
    }

    public Hotel getHotelById(Long id) {
        return HOTEL_REPOSITORY.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
    }
}
