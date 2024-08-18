package lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeListRepository extends JpaRepository<LikeList, Long> {
    // 사용자 ID와 호텔 ID로 LikeList 조회
    LikeList findByUserIdAndHotelId(Long userId, Long hotelId);

    LikeList deleteByUserIdAndHotelId(Long userId, Long hotelId);

    // 사용자 ID로 LikeList 목록 조회
    List<LikeList> findByUserId(long userId);
}