package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT c.id, h.name AS hotelName, r.name AS roomName, c.checkin_date AS checkInDate, c.checkout_date AS checkOutDate, r.price, i.path AS imgPath, h.id AS hotelId, h.grade, u.grade AS userGrade, r.id AS roomId, " +
            "CASE " +
            "    WHEN r.quantity > (SELECT COUNT(bl.id) " +
            "                      FROM booking_list bl " +
            "                      WHERE bl.room_id = r.id " +
            "                      AND bl.cancel = 0 " +
            "                      AND bl.check_in_date <= c.checkout_date " +
            "                      AND bl.check_out_date >= c.checkin_date) " +
            "    THEN 1 " +
            "    ELSE 0 " +
            "END AS isAvailable " +
            "FROM cart c " +
            "INNER JOIN room r ON c.room_id = r.id " +
            "INNER JOIN hotel h ON r.hotel_id = h.id " +
            "LEFT JOIN img i ON r.img_id = i.id " +
            "INNER JOIN user u ON c.user_id = u.id " +
            "WHERE c.user_id = :userId " +
            "ORDER BY c.id DESC", nativeQuery = true)
    List<Object[]> findCartWithAvailabilityByUserId(@Param("userId") Long userId);




}
