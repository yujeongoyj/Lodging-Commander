package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.service.ModifyHotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/properties/ModifyHotel")
public class ModifyHotelController {

    private final ModifyHotelService modifyHotelService;

    public ModifyHotelController(ModifyHotelService modifyHotelService) {
        this.modifyHotelService = modifyHotelService;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HotelDTO>> getHotelsByUserId(@PathVariable Long userId) {
        List<HotelDTO> hotels = modifyHotelService.findHotelsByUserId(userId);
        return ResponseEntity.ok(hotels);
    }
}
