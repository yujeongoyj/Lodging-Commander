package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.hotel.HotelResponseDTO;
import com.hotel.lodgingCommander.model.MapDTO;
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
@CrossOrigin(origins = "http://localhost:3000")
public class HotelController {
    private HotelService HOTEL_SERVICE;

    @GetMapping("details/{id}")
    public ResponseEntity<Map<String, Object>> details(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("hotel", HOTEL_SERVICE.getHotelById(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("details/like/{id}")
    public ResponseEntity<Map<String, String>> getHotelDetails(@PathVariable Long id) {
        HotelResponseDTO hotel = HOTEL_SERVICE.getHotelById(id);
        Map<String, String> response = new HashMap<>();
        response.put("id", String.valueOf(hotel.getId()));
        response.put("hotelName", hotel.getHotelName());
        return ResponseEntity.ok(response);
    }

    @GetMapping("details/map/{hotelId}")

    public MapDTO getAddressByHotelId(@PathVariable Long hotelId) {
        return HOTEL_SERVICE.getAddressByHotelId(hotelId);
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


    @GetMapping("{location}")
    public ResponseEntity<Map<String, Object>> getListByLocation(@PathVariable String location){
        Map<String, Object> response = new HashMap<>();
        response.put("hotelList",HOTEL_SERVICE.findByLocation(location));
        return ResponseEntity.ok(response);
    }

    @GetMapping({"newHotels"})
    public ResponseEntity<Map<String, Object>> getNewHotels(){
        Map<String, Object> response = new HashMap<>();
        response.put("hotelList",HOTEL_SERVICE.getRecentHotels());
        return ResponseEntity.ok(response);
    }

    @GetMapping("names")
    public ResponseEntity<Map<Long, String>> getHotelNames(@RequestParam("ids") List<Long> ids) {
        Map<Long, String> hotelNames = HOTEL_SERVICE.getHotelNamesByIds(ids);
        return ResponseEntity.ok(hotelNames);
    }
}
