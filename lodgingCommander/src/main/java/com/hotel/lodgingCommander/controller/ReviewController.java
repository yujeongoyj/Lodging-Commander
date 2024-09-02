package com.hotel.lodgingCommander.controller;
import com.hotel.lodgingCommander.model.ReviewModel;
import com.hotel.lodgingCommander.service.ReviewService;
import com.hotel.lodgingCommander.service.impl.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reviews")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl service;

    @PostMapping
    public ResponseEntity<?> wirte(@RequestBody ReviewModel reviewDTO) {
        return ResponseEntity.ok(service.createReview(reviewDTO));
    }

    //userid받아서 리뷰 확인
    @GetMapping("/listByUserId")
    public ResponseEntity<?> findReviewsByUserId(@RequestParam Long userId) {
        return ResponseEntity.ok(service.findReviewsByUserId(userId));
    }

    //hotelid받아서 리뷰 확인
    @GetMapping("/findByHotel")
    public ResponseEntity<?> findReviewsByHotelId(@RequestParam Long hotelId) {
        return ResponseEntity.ok(service.findReviewsByHotelId(hotelId));
    }


    //id받아서 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeReview(@PathVariable Long id) {
        return ResponseEntity.ok(service.removeReview(id));
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewModel reviewDTO) {
        return ResponseEntity.ok(service.updateReview(id, reviewDTO));
    }



}