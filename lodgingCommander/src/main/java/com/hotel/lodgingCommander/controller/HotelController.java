package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelService.saveHotel(hotel);
    }

    @PutMapping("/{propertyId}")
    public Hotel updateHotel(@PathVariable Long propertyId, @RequestBody Hotel hotelDetails) {
        return hotelService.updateHotel(propertyId, hotelDetails);
    }

    @DeleteMapping("/{propertyId}")
    public void deleteHotel(@PathVariable Long propertyId) {
        hotelService.deleteHotel(propertyId);
    }
}
