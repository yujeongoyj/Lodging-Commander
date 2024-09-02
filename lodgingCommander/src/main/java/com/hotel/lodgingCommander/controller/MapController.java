package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.MapDTO;
import com.hotel.lodgingCommander.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel")
@CrossOrigin("http://localhost:3000")
public class MapController {

    private final HotelService hotelService;

    @Autowired
    public MapController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{hotelId}")
    public MapDTO getAddressByHotelId(@PathVariable Long hotelId) {
        return hotelService.getAddressByHotelId(hotelId);
    }
}