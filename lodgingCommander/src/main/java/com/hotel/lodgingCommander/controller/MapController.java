package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.MapModel;
import com.hotel.lodgingCommander.service.HotelService;
import com.hotel.lodgingCommander.service.impl.HotelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/maps")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
public class MapController {

    private final HotelServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getAddressByHotelId(@RequestParam Long hotelId) {
        return ResponseEntity.ok(service.getAddressByHotelId(hotelId));
    }
}