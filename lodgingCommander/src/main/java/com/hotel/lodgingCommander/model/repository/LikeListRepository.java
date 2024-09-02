package com.hotel.lodgingCommander.model.repository;

import com.hotel.lodgingCommander.model.entity.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList, Long> {
    LikeList findByUserIdAndHotelId(Long userId, Long hotelId);

    List<LikeList> findByUserId(long userId);
}