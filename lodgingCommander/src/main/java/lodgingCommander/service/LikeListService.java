package lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.LikeList;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.model.LikeListDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.LikeListRepository;
import com.hotel.lodgingCommander.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeListService {

    private final LikeListRepository likeListRepository;
    private final UserRepository userRepository;
    private final HotelRepository hotelRepository;

    @Transactional
    public void addLike(LikeListDTO likeListDTO) {
        Optional<User> optionalUser = userRepository.findById(likeListDTO.getUserId());
        Optional<Hotel> optionalHotel = hotelRepository.findById(likeListDTO.getHotelId());

        if (optionalUser.isPresent() && optionalHotel.isPresent()) {
            LikeList likeList = new LikeList();
            likeList.setUser(optionalUser.get());
            likeList.setHotel(optionalHotel.get());
            likeListRepository.save(likeList);
        } else {
            throw new RuntimeException("User or Hotel not found");
        }
    }

    @Transactional
    public void removeLike(LikeListDTO likeListDTO) {
        LikeList likeList = likeListRepository.findByUserIdAndHotelId(likeListDTO.getUserId(), likeListDTO.getHotelId());
        if (likeList != null) {
            likeListRepository.delete(likeList);
        } else {
            throw new RuntimeException("Like not found");
        }
    }
    public void removeLikeById(Long id) {
        likeListRepository.deleteById(id);
    }


    public List<LikeListDTO> getLikesByUserId(long userId) {
        List<LikeList> likeLists = likeListRepository.findByUserId(userId);
        return likeLists.stream()
                .map(likeList -> new LikeListDTO(likeList.getId(),
                        likeList.getUser().getId(),
                        likeList.getHotel().getId()))
                .collect(Collectors.toList());
    }

    public LikeListDTO findByUserIdAndHotelId(Long userId, Long hotelId) {
        LikeList likeList = likeListRepository.findByUserIdAndHotelId(userId, hotelId);
        if (likeList != null) {
            return new LikeListDTO(likeList.getId(),
                    likeList.getUser().getId(),
                    likeList.getHotel().getId());
        }
        return null;
    }
}