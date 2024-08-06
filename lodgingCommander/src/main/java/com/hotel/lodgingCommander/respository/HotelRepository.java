package com.hotel.lodgingCommander.respository;

import com.hotel.lodgingCommander.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {}