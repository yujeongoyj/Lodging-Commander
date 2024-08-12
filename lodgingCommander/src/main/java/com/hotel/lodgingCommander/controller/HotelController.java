package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.hotel.HotelResponseDTO;
import com.hotel.lodgingCommander.sercvice.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
public class HotelController {

    private HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> getSearchList(
            @RequestParam(name = "location") String location,
            @RequestParam(name = "checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(name = "checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(name = "guests") int guests,
            @RequestParam(name = "rooms") int rooms) {

        Map<String, Object> response = new HashMap<>();

        int adjustedGuests = (guests % rooms == 0)
                ? guests / rooms
                : guests / rooms + 1;

        List<HotelResponseDTO> searchList = hotelService.findAvailableHotels(
                location,
                checkInDate,
                checkOutDate,
                adjustedGuests,
                rooms
        );

        response.put("searchList", searchList);

        return ResponseEntity.ok(response);
    }


}
