package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.dto.room.RoomResponseDTO;
import com.hotel.lodgingCommander.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT MIN(r.price) FROM Room r WHERE r.hotel.id = :hotelId")
    int findMinPriceByHotelId(@Param("hotelId") Long hotelId);


    @Query("SELECT new com.hotel.lodgingCommander.dto.room.RoomResponseDTO(" +
            "r.id, " +
            "r.name, " +
            "r.maxPeople, " +
            "r.price, " +
            "r.detail, " +
            "CASE WHEN COALESCE(COUNT(bl.room.id), 0) = r.quantity THEN true ELSE false END, " +
            "i.path," +
            "h.name," +
            "h.id) " +
            "FROM Room r " +
            "INNER JOIN Hotel h ON h.id = r.hotel.id " +
            "INNER JOIN Img i ON i.id = r.img.id " +
            "LEFT JOIN BookingList bl ON r.id = bl.room.id " +
            "AND ((bl.checkInDate >= :checkInDate AND bl.checkInDate <= :checkOutDate) OR" +
            "(bl.checkOutDate >= :checkInDate AND bl.checkOutDate <= :checkOutDate))" +
            "AND h.id = :hotelId " +
            "AND bl.cancel = false" +
            " WHERE h.id = :hotelId " +
            "GROUP BY r.id, r.name, r.maxPeople, r.price, r.detail, r.quantity, i.path")
    List<RoomResponseDTO> findRoomsWithBookingStatus(
            @Param("hotelId") Long hotelId,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);


}
