package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.LikeListModel;
import com.hotel.lodgingCommander.model.entity.LikeList;
import com.hotel.lodgingCommander.model.repository.HotelRepository;
import com.hotel.lodgingCommander.model.repository.LikeListRepository;
import com.hotel.lodgingCommander.model.repository.UserRepository;
import com.hotel.lodgingCommander.service.LikeListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeListServiceImpl implements LikeListService {

    private final LikeListRepository likeListRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    @Transactional
    public Map<?, ?> addLike(LikeListModel likeListDTO) {
        var optionalUser = userRepository.findById(likeListDTO.getUserId());
        var optionalHotel = hotelRepository.findById(likeListDTO.getHotelId());

        if (optionalUser.isPresent() && optionalHotel.isPresent()) {
            LikeList likeList = new LikeList();
            likeList.setUser(optionalUser.get());
            likeList.setHotel(optionalHotel.get());
            likeListRepository.save(likeList);
        } else {
            throw new RuntimeException("User or Hotel not found");
        }
        var resultMap = new HashMap<>();
        try {
            resultMap.put("userId", likeListDTO.getUserId());
            resultMap.put("hotelId", likeListDTO.getHotelId());
            resultMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @Transactional
    public Boolean removeLike(LikeListModel likeListDTO) {
        var likeList = likeListRepository.findByUserIdAndHotelId(likeListDTO.getUserId(), likeListDTO.getHotelId());
        likeListRepository.delete(likeList);
        return likeListRepository.findById(likeListDTO.getId()) == null ? true : false;
    }

    public Boolean removeLikeById(Long id) {
        likeListRepository.deleteById(id);
        return likeListRepository.findById(id) == null ? true : false;
    }


    public List<?> getLikesByUserId(long userId) {
        return likeListRepository.findByUserId(userId).stream()
                .map(likeList -> new LikeListModel(likeList.getId(),
                        likeList.getUser().getId(),
                        likeList.getHotel().getId()))
                .collect(Collectors.toList());
    }

    public LikeListModel findByUserIdAndHotelId(Long userId, Long hotelId) {
        var likeList = likeListRepository.findByUserIdAndHotelId(userId, hotelId);
        return new LikeListModel(
                likeList.getId(),
                likeList.getUser().getId(),
                likeList.getHotel().getId()
        );
    }

}
