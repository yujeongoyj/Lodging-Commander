package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.hotel.HotelRequestModel;
import com.hotel.lodgingCommander.service.impl.HotelServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping({"/hotels"})
@CrossOrigin(origins = "http://localhost:3000")
public class HotelController {
    private final HotelServiceImpl service;

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getHotelDetail(@PathVariable Long id) {
        return ResponseEntity.ok(service.getHotelById(id));
    }

    @GetMapping("/details/map/{hotelId}")
    public ResponseEntity<?> getAddressByHotelId(@PathVariable Long hotelId) {
        return ResponseEntity.ok(service.getAddressByHotelId(hotelId));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getSearchList(
            @RequestParam(name = "location") String location,
            @RequestParam(name = "checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(name = "checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(name = "guests") int guests,
            @RequestParam(name = "rooms") int rooms) {
        return ResponseEntity.ok(service.findAvailableHotels(
                location,
                checkInDate,
                checkOutDate,
                guests,
                rooms
        ));
    }


    @GetMapping("/{location}")
    public ResponseEntity<?> getListByLocation(@PathVariable String location) {
        return ResponseEntity.ok(service.findByLocation(location));
    }

    @GetMapping({"/newHotels"})
    public ResponseEntity<?> getNewHotels() {
        return ResponseEntity.ok(service.getRecentHotels());
    }

    @GetMapping("/names")
    public ResponseEntity<?> getHotelName(@RequestParam("ids") List<Long> ids) {
        return ResponseEntity.ok(service.getHotelNamesByIds(ids));
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> saveHotel(@RequestBody HotelRequestModel hotelDTO) {
        return ResponseEntity.ok(service.saveHotel(hotelDTO));
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<?>> getHotelsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(service.getHotelsByUserId(userId));
    }
}
