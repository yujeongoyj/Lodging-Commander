package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.dto.hotel.HotelResponseDTO;
import com.hotel.lodgingCommander.service.HotelService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping({"/hotel/"})
public class HotelController {
    private HotelService HOTEL_SERVICE;

    @GetMapping("details/{id}")
    public ResponseEntity<Hotel> details(@PathVariable Long id) {
        System.out.println(id);
        return ResponseEntity.ok(HOTEL_SERVICE.getHotelById(id));

    }

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

        List<HotelResponseDTO> searchList = HOTEL_SERVICE.findAvailableHotels(
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
