package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddHotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findByUserId(Long userId);
}
