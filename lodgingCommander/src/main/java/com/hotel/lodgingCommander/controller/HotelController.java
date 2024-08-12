package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping({"/hotel/"})
public class HotelController {
    private HotelService HOTEL_SERVICE;

    @GetMapping("details/{id}")
    public ResponseEntity<Hotel> details(@PathVariable Long id) {
        return ResponseEntity.ok(HOTEL_SERVICE.getHotelById(id));
    }
}
