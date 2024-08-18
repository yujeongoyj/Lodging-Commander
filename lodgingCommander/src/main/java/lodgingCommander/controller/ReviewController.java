package lodgingCommander.controller;

import com.hotel.lodgingCommander.model.ReviewDTO;
import com.hotel.lodgingCommander.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/review")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    @Autowired
    private ReviewService REVIEW_SERVICE;

    @PostMapping("/add")
    public Map<String, Object> addReview(@RequestBody ReviewDTO reviewDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            REVIEW_SERVICE.createReview(reviewDTO);
            resultMap.put("userId", reviewDTO.getUserId());
            resultMap.put("hotelId", reviewDTO.getHotelId());
            resultMap.put("content", reviewDTO.getContent());
            resultMap.put("rating", reviewDTO.getRating());
            resultMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    //userid받아서 리뷰 확인
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> findReviewsByUserId(@PathVariable Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ReviewDTO> reviews = REVIEW_SERVICE.findReviewsByUserId(userId);
            resultMap.put("reviews", reviews);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("error", "Failed to fetch reviews");
            return ResponseEntity.status(500).body(resultMap);
        }
    }

    //hotelid받아서 리뷰 확인
    @GetMapping("/hotel/{hotelId}")
    public ResponseEntity<Map<String, Object>> findReviewsByHotelId(@PathVariable Long hotelId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            List<ReviewDTO> reviews = REVIEW_SERVICE.findReviewsByHotelId(hotelId);
            resultMap.put("reviews", reviews);
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("error", "Failed to fetch reviews");
            return ResponseEntity.status(500).body(resultMap);
        }
    }


    //id받아서 리뷰 삭제
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> removeReview(@PathVariable Long id) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            REVIEW_SERVICE.removeReview(id);
            resultMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    // 리뷰 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateReview(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ReviewDTO updatedReview = REVIEW_SERVICE.updateReview(id, reviewDTO);
            resultMap.put("review", updatedReview);
            resultMap.put("result", "success");
            return ResponseEntity.ok(resultMap);
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
            resultMap.put("error", "Failed to update review");
            return ResponseEntity.status(500).body(resultMap);
        }
    }
}