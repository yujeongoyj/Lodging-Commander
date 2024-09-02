package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.AddressModel;
import com.hotel.lodgingCommander.service.impl.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {
    private final AddressServiceImpl service;


    @PostMapping("/address")
    public ResponseEntity<?> saveAddress(@RequestBody AddressModel addressDTO) {
        return ResponseEntity.ok(service.save(addressDTO));
    }
}
