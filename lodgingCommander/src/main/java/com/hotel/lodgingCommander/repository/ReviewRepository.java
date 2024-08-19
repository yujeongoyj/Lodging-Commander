    package com.hotel.lodgingCommander.repository;

    import com.hotel.lodgingCommander.entity.Review;
    import com.hotel.lodgingCommander.entity.Room;
    import lombok.Lombok;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface ReviewRepository extends JpaRepository<Review, Long> {

        int countByHotel_Id(Long hotelId);
        List<Review> findByHotelId(Long hotelId);
        List<Review> findByUserId(Long userId);
    }
