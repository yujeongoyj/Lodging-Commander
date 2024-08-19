package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.Review;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.model.ReviewDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.ReviewRepository;
import com.hotel.lodgingCommander.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private UserRepository userRepository;

    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // 호텔과 사용자 엔티티를 데이터베이스에서 조회
        Optional<Hotel> hotel = hotelRepository.findById(reviewDTO.getHotelId());
        Optional<User> user = userRepository.findById(reviewDTO.getUserId());

        if (hotel.isEmpty() || user.isEmpty()) {
            throw new IllegalArgumentException("Hotel or User not found");
        }

        Review review = Review.builder()
                .hotel(hotel.get())
                .user(user.get())
                .rating(reviewDTO.getRating())
                .content(reviewDTO.getContent())
                .createDate(new Date())
                .build();

        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

    public List<ReviewDTO> findReviewsByUserId(Long userId) {
        List<Review> reviews = reviewRepository.findByUserId(userId);
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ReviewDTO> findReviewsByHotelId(Long HotelId) {
        List<Review> reviews = reviewRepository.findByHotelId(HotelId);
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ReviewDTO findReviewById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        return review.map(this::convertToDTO).orElse(null);
    }

    private ReviewDTO convertToDTO(Review review) {
        return new ReviewDTO(
                review.getId(),
                review.getHotel().getId(),
                review.getHotel().getName(),
                review.getUser().getId(),
                review.getRating(),
                review.getContent(),
                review.getCreateDate()
        );
    }
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO) {
        Optional<Review> optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            throw new IllegalArgumentException("Review not found");
        }

        Review review = optionalReview.get();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());

        review = reviewRepository.save(review);
        return convertToDTO(review);
    }

    public void removeReview(Long id) {
        reviewRepository.deleteById(id);
    }


}