/*
package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @PostMapping
    public HotelDTO createHotel(@RequestBody HotelDTO hotelRequestDTO) {
        return hotelService.saveHotel(hotelRequestDTO);
    }

    @PutMapping("/{propertyId}")
    public HotelDTO updateHotel(@PathVariable Long propertyId, @RequestBody HotelDTO hotelRequestDTO) {
        return hotelService.updateHotel(propertyId, hotelRequestDTO);
    }

    @DeleteMapping("/{propertyId}")
    public void deleteHotel(@PathVariable Long propertyId) {
        hotelService.deleteHotel(propertyId);
    }
}
*/
