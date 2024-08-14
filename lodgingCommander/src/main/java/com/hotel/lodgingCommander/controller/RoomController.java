package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.entity.Room;
import com.hotel.lodgingCommander.repository.RoomRepository;
import com.hotel.lodgingCommander.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class RoomController {

    private RoomRepository roomRepository;
    private RoomService ROOM_SERVICE;

    @GetMapping("/rooms")
    public ResponseEntity<Map<String, Object>> getRoomList(@RequestParam Long hotelId,
                                                           @RequestParam LocalDate checkInDate,
                                                           @RequestParam LocalDate checkOutDate) {
        Map<String, Object> response = new HashMap<>();

        response.put("roomList", ROOM_SERVICE.getRoomsWithBookingStatus(hotelId, checkInDate, checkOutDate));
        return ResponseEntity.ok(response);
    }
}
