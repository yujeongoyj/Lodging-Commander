package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.LikeListModel;

import java.util.List;
import java.util.Map;

public interface LikeListService {

    Map<?, ?> addLike(LikeListModel likeListDTO);

    Boolean removeLike(LikeListModel likeListDTO);
    Boolean removeLikeById(Long id);


    List<?> getLikesByUserId(long userId) ;

    LikeListModel findByUserIdAndHotelId(Long userId, Long hotelId);
}