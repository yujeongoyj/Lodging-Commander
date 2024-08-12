package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/hotel/"})
public class HotelController {
    private final HotelService HOTEL_SERVICE;

    public HotelController(HotelService hotelService) {
        HOTEL_SERVICE = hotelService;
    }

    @GetMapping("details/{id}")
    public ResponseEntity<Hotel> details(@PathVariable Long id) {
        return ResponseEntity.ok(HOTEL_SERVICE.getHotelById(id));
    }
}
