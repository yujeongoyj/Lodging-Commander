package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.bookingList.BookingListRequestModel;
import com.hotel.lodgingCommander.service.impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingController {
    private final BookingServiceImpl service;

    @GetMapping("/showOne")
    public ResponseEntity<?> showBookingPage(@RequestParam Long roomId) {
        return ResponseEntity.ok(service.showBookingPage(roomId));
    }

    @PostMapping("/booking/{id}")
    public ResponseEntity<?> requestBooking(@PathVariable Long id, @RequestBody BookingListRequestModel requestModel) {
        return ResponseEntity.ok(service.createBooking(requestModel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateState(@PathVariable("id") Long bookingId) {
        return ResponseEntity.ok(service.cancelBooking(bookingId));
    }

    @GetMapping("/validBooking/{id}")
    public ResponseEntity<?> validBooking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(service.myAllValidBooking(userId));
    }

    @GetMapping("/expiredBooking/{id}")
    public ResponseEntity<?> expiredBooking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(service.myAllExpiredBooking(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(service.myAllCancelBooking(userId));
    }

}
