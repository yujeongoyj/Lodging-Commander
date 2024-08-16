package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.repository.AddHotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModifyHotelService {

    private final AddHotelRepository addHotelRepository;

    public ModifyHotelService(AddHotelRepository addHotelRepository) {
        this.addHotelRepository = addHotelRepository;
    }

    @Transactional
    public List<HotelDTO> findHotelsByUserId(Long userId) {
        return addHotelRepository.findByUserId(userId).stream()
                .map(hotel -> new HotelDTO(hotel))
                .collect(Collectors.toList());
    }
}
