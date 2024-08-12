package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.model.MapDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public MapDTO getAddressByHotelId(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId));

        Address address = hotel.getAddress(); // Hotel의 Address 가져오기

        if (address == null) {
            throw new IllegalArgumentException("Address not found for hotel with id: " + hotelId);
        }

        return new MapDTO(address.getId(), address.getLatitude(), address.getLongitude());
    }
}