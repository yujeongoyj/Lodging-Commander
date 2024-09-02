package com.hotel.lodgingCommander.model.repository;

import com.hotel.lodgingCommander.model.entity.Hotel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findById(Long id);

    @Transactional
    @Query(value = """
            SELECT DISTINCT h, c.name
            FROM Hotel h
            INNER JOIN h.address a
            INNER JOIN h.rooms r
            INNER JOIN h.category c
            INNER JOIN h.facility f
            WHERE a.address LIKE %:location%
              AND r.maxPeople >= :guests
              AND h.id IN (
                SELECT r2.hotel.id 
                FROM Room r2 
                WHERE r2.hotel.id = h.id
                GROUP BY r2.hotel.id
                HAVING MAX(r2.quantity) - (
                    CASE 
                        WHEN 0 < (
                            SELECT COUNT(bl) 
                            FROM BookingList bl 
                            INNER JOIN bl.room r3 
                            WHERE r3.hotel.id = h.id 
                              AND (bl.checkInDate <= :checkOutDate AND bl.checkOutDate >= :checkInDate)
                              AND bl.cancel = false
                        ) 
                        THEN (
                            SELECT COUNT(bl) 
                            FROM BookingList bl 
                            INNER JOIN bl.room r3 
                            WHERE r3.hotel.id = h.id 
                              AND (bl.checkInDate <= :checkOutDate AND bl.checkOutDate >= :checkInDate)
                              AND bl.cancel = false
                        )
                        ELSE 0 
                    END
                ) >= :rooms
                  AND MAX(r2.quantity) > (
                    CASE 
                        WHEN 0 < (
                            SELECT COUNT(bl) 
                            FROM BookingList bl 
                            INNER JOIN bl.room r3 
                            WHERE r3.hotel.id = h.id 
                              AND (bl.checkInDate <= :checkOutDate AND bl.checkOutDate >= :checkInDate)
                              AND bl.cancel = false
                        ) 
                        THEN (
                            SELECT COUNT(bl) 
                            FROM BookingList bl 
                            INNER JOIN bl.room r3 
                            WHERE r3.hotel.id = h.id 
                              AND (bl.checkInDate <= :checkOutDate AND bl.checkOutDate >= :checkInDate)
                              AND bl.cancel = false
                        )
                        ELSE 0 
                    END
                )
            )
            """,
            nativeQuery = false)
    List<Hotel> findAvailableHotels(
            @Param("location") String location,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate,
            @Param("guests") int guests,
            @Param("rooms") int rooms
    );

    @Query("SELECT h FROM Hotel h INNER JOIN Address a ON h.address.id = a.id WHERE a.address LIKE :location%")
    List<Hotel> findByLocation(@Param("location") String location);

    @Query("SELECT h FROM Hotel h ORDER BY h.id DESC")
    List<Hotel> findByRecentlyList(Pageable pageable);

    @Query("SELECT h.id, h.name FROM Hotel h WHERE h.id IN :ids")
    List<Object[]> findNamesByIds(@Param("ids") List<Long> ids);

    List<Hotel> findByUserId(Long userId);
}
