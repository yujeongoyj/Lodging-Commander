package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.FacilityModel;
import com.hotel.lodgingCommander.service.impl.FacilityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facilities")
@RequiredArgsConstructor
public class FacilityController {

    private final FacilityServiceImpl service;

    @PostMapping
    public ResponseEntity<?> saveFacility(@RequestParam("hotelId") Long hotelId, @RequestBody FacilityModel facilityDTO) {
        return ResponseEntity.ok(service.save(facilityDTO, hotelId));
    }
}
