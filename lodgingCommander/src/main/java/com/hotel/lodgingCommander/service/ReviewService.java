package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.ReviewModel;

import java.util.List;

public interface ReviewService {

    Boolean createReview(ReviewModel reviewDTO);

    List<?> findReviewsByUserId(Long userId);

    List<?> findReviewsByHotelId(Long HotelId);

    ReviewModel findReviewById(Long id);

    Boolean updateReview(Long id, ReviewModel reviewDTO);

    Boolean removeReview(Long id);
}