package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.ReviewModel;
import com.hotel.lodgingCommander.model.entity.Review;
import com.hotel.lodgingCommander.model.repository.HotelRepository;
import com.hotel.lodgingCommander.model.repository.ReviewRepository;
import com.hotel.lodgingCommander.model.repository.UserRepository;
import com.hotel.lodgingCommander.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;

    public Boolean createReview(ReviewModel reviewDTO) {
        var hotel = hotelRepository.findById(reviewDTO.getHotelId());
        var user = userRepository.findById(reviewDTO.getUserId());

        if (hotel.isEmpty() || user.isEmpty()) {
            throw new IllegalArgumentException("Hotel or User not found");
        }

        var review = Review.builder()
                .hotel(hotel.get())
                .user(user.get())
                .rating(reviewDTO.getRating())
                .content(reviewDTO.getContent())
//                .createDate(new Date())
                .build();

        var reviewModel = convertToDTO(reviewRepository.save(review));
        return reviewModel.getId() == null ? false : true;
    }


    public List<?> findReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<?> findReviewsByHotelId(Long HotelId) {
        return reviewRepository.findByHotelId(HotelId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ReviewModel findReviewById(Long id) {
        return convertToDTO(reviewRepository.findById(id).get());
    }

    private ReviewModel convertToDTO(Review review) {
        return new ReviewModel(
                review.getId(),
                review.getHotel().getId(),
                review.getUser().getId(),
                review.getRating(),
                review.getContent(),
//                review.getCreateDate(),
                review.getHotel().getName()
        );
    }

    public Boolean updateReview(Long id, ReviewModel reviewDTO) {
        var optionalReview = reviewRepository.findById(id);

        if (optionalReview.isEmpty()) {
            throw new IllegalArgumentException("Review not found");
        }

        Review review = optionalReview.get();
        review.setContent(reviewDTO.getContent());
        review.setRating(reviewDTO.getRating());

        var reviewModel = convertToDTO(reviewRepository.save(review));
        return reviewModel.getId() == null ? false : true;
    }

    public Boolean removeReview(Long id) {
        reviewRepository.deleteById(id);
        return reviewRepository.findById(id) == null ? true : false;
    }

}
