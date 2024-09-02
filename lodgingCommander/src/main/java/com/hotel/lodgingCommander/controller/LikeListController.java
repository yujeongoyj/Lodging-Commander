package com.hotel.lodgingCommander.controller;

import com.hotel.lodgingCommander.model.LikeListModel;
import com.hotel.lodgingCommander.service.LikeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/likelist")
@RequiredArgsConstructor
public class LikeListController {

    private final LikeListService service;

    @PostMapping
    public ResponseEntity<?> addLike(@RequestBody LikeListModel likeListDTO) {
        return ResponseEntity.ok(service.addLike(likeListDTO));
    }

    @GetMapping("/listByUser")
    public ResponseEntity<?> getLikesByUserId(@RequestParam long userId) {
        return ResponseEntity.ok(service.getLikesByUserId(userId));
    }

    // 좋아요 상태 조회
    @GetMapping("/{userId}/{hotelId}")
    public ResponseEntity<?> getLikeStatus(@PathVariable Long userId, @PathVariable Long hotelId) {
        return ResponseEntity.ok(service.findByUserIdAndHotelId(userId, hotelId));
    }

    // 찜목록에서 좋아요 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeLikeById(@PathVariable Long id) {
        return ResponseEntity.ok(service.removeLikeById(id));
    }
}