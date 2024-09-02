package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.dto.bookingList.BookingListRequestDTO;
import com.hotel.lodgingCommander.dto.room.RoomResponseDTO;
import com.hotel.lodgingCommander.service.BookingService;
import com.hotel.lodgingCommander.service.CartService;
import com.hotel.lodgingCommander.service.FacilityService;
import com.hotel.lodgingCommander.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class BookingController {
    private final BookingService BOOKING_SERVICE;
    private final RoomService ROOM_SERVICE;
    private final FacilityService FACILITY_SERVICE;
    private final CartService CART_SERVICE;


    @RequestMapping("/booking/{id}")
    public ResponseEntity<Map<String, Object>> responseBooking(@PathVariable("id") Long roomId) {
        Map<String, Object> map = new HashMap<>();
        RoomResponseDTO roomResponseDTO = ROOM_SERVICE.selectOneRoom(roomId);
        map.put("RoomResponseDTO", roomResponseDTO);
        map.put("FacilityList", FACILITY_SERVICE.getList(roomResponseDTO.getHotelId()));
        return ResponseEntity.ok(map);
    }

    @PostMapping("/booking/{id}")
    public void requestBooking(@PathVariable Long id, @RequestBody BookingListRequestDTO requestDTO) {
        try {
            BOOKING_SERVICE.createBooking(requestDTO);
            if (requestDTO.getCartId() != null) {
                CART_SERVICE.delete(requestDTO.getCartId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/booking/cancel/{id}")
    public void updateBooking(@PathVariable("id") Long bookingId) {
        BOOKING_SERVICE.cancelBooking(bookingId);
    }

    @RequestMapping("/validBooking/{id}")
    public ResponseEntity<Map<String, Object>> validBooking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(BOOKING_SERVICE.myAllValidBooking(userId));
    }

    @RequestMapping("/expiredBooking/{id}")
    public ResponseEntity<Map<String, Object>> expiredBooking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(BOOKING_SERVICE.myAllExpiredBooking(userId));
    }

    @RequestMapping("/cancelBooking/{id}")
    public ResponseEntity<Map<String, Object>> cancelBooking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(BOOKING_SERVICE.myAllCancelBooking(userId));
    }

}
