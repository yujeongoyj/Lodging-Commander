package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.model.LikeListDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.LikeListRepository;
import com.hotel.lodgingCommander.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotel.lodgingCommander.entity.LikeList;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor()
public class LikeListService {

    @Autowired
    private LikeListRepository likeListRepository;
    private UserRepository userRepository;
    private HotelRepository hotelRepository;


    public LikeListService(LikeListRepository likeListRepository) {
            this.likeListRepository = likeListRepository;
        }

        public void delete(Long id) {
            likeListRepository.deleteById(id);
        }

    // 좋아요 추가
    public void addLike(LikeListDTO likeListDTO) {
        User user = userRepository.findById(likeListDTO.getUserId()).orElse(null);
        Hotel hotel = hotelRepository.findById(likeListDTO.getHotelId()).orElse(null);

        if (user != null && hotel != null) {
            LikeList likeList = new LikeList();
            likeList.setUser(user);
            likeList.setHotel(hotel);
            likeListRepository.save(likeList);
        }
    }

    // 좋아요 삭제
    public void removeLike(LikeListDTO likeListDTO) {
        LikeList likeList = likeListRepository.findByUserIdAndHotelId(likeListDTO.getUserId(), likeListDTO.getHotelId());
        if (likeList != null) {
            likeListRepository.delete(likeList);
        }
    }

    public List<LikeListDTO> getLikesByUserId(long userId) {
        List<LikeList> likeLists = likeListRepository.findByUserId(userId);
        return likeLists.stream()
                .map(likeList -> new LikeListDTO(likeList.getId(),
                        likeList.getUser().getId(),
                        likeList.getHotel().getId()))
                .collect(Collectors.toList());
    }
}
