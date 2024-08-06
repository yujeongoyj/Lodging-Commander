package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.respository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel hotelDetails) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            hotel.setName(hotelDetails.getName());
            hotel.setUser(hotelDetails.getUser());
            hotel.setAddress(hotelDetails.getAddress());
            hotel.setCategory(hotelDetails.getCategory());
            hotel.setTel(hotelDetails.getTel());
            hotel.setGrade(hotelDetails.getGrade());
            hotel.setDetail(hotelDetails.getDetail());
            return hotelRepository.save(hotel);
        } else {
            throw new RuntimeException("Hotel not found");
        }

    }

    public void deleteHotel(Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            hotelRepository.delete(hotelOptional.get());
        }
    }
}
