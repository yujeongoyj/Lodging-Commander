package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.service.impl.ImgServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/imgs")
@RequiredArgsConstructor
public class ImgController {

    private ImgServiceImpl service;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        return ResponseEntity.ok(service.save(image));
    }
}
