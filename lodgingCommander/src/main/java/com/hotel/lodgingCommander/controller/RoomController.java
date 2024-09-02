package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.room.RoomRequestModel;
import com.hotel.lodgingCommander.service.impl.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomServiceImpl service;

    @GetMapping
    public ResponseEntity<?> getList(@RequestParam Long hotelId,
                                     @RequestParam LocalDate checkInDate,
                                     @RequestParam LocalDate checkOutDate) {

        return ResponseEntity.ok(service.getRoomsWithBookingStatus(hotelId, checkInDate, checkOutDate));
    }

    @PostMapping
    public ResponseEntity<?> saveRoom(@RequestBody RoomRequestModel roomDTO) {
        return ResponseEntity.ok(service.saveRoom(roomDTO));
    }
}
