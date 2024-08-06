package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {}