package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.FacilityModel;
import com.hotel.lodgingCommander.model.entity.Facility;
import com.hotel.lodgingCommander.model.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

public interface FacilityService {
    Boolean save(FacilityModel facilityDTO, Long hotelId);
}
