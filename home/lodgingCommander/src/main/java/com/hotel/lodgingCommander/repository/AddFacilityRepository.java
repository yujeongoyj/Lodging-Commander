package com.hotel.lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddFacilityRepository extends JpaRepository<Facility, Long> {
}
