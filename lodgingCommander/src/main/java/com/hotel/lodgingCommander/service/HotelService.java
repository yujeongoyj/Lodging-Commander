package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.model.MapDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {
    private final HotelRepository HOTEL_REPOSITORY;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        HOTEL_REPOSITORY = hotelRepository;
    }

    public MapDTO getAddressByHotelId(Long hotelId) {
        Hotel hotel = HOTEL_REPOSITORY.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId));

        Address address = hotel.getAddress(); // Hotel의 Address 가져오기

        if (address == null) {
            throw new IllegalArgumentException("Address not found for hotel with id: " + hotelId);
        }
        return new MapDTO(address.getId(), address.getLatitude(), address.getLongitude());
    }
    public Hotel getHotelById(Long id) {
        return HOTEL_REPOSITORY.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

}