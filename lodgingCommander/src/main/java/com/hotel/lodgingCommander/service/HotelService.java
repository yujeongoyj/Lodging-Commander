package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.hotel.HotelResponseDTO;
import com.hotel.lodgingCommander.entity.Facility;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.ImgRepository;
import com.hotel.lodgingCommander.repository.ReviewRepository;
import com.hotel.lodgingCommander.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelService {
    private HotelRepository HOTEL_REPOSITORY;
    private RoomRepository ROOM_REPOSITORY;
    private ReviewRepository REVIEW_REPOSITORY;
    private ImgRepository IMG_REPOSITORY;
    private FacilityService FACILITY_SERVICE;


    public Hotel getHotelById(Long id) {
        return HOTEL_REPOSITORY.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found"));
    }

    @Transactional(readOnly = true)
    public List<HotelResponseDTO> findAvailableHotels(String location, LocalDate checkInDate, LocalDate checkOutDate, int guests, int rooms) {
        List<Hotel> availableHotels = HOTEL_REPOSITORY.findAvailableHotels(location, checkInDate, checkOutDate, guests, rooms);
        return availableHotels.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private HotelResponseDTO convertToDTO(Hotel hotel) {
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
        hotelResponseDTO.setId(hotel.getId());
        hotelResponseDTO.setHotelName(hotel.getName());
        hotelResponseDTO.setDetail(hotel.getDetail());
        hotelResponseDTO.setGrade(hotel.getGrade());
        hotelResponseDTO.setImgPath(IMG_REPOSITORY.findPathListByHotelId(hotel.getId()));
        hotelResponseDTO.setMinPrice(ROOM_REPOSITORY.findMinPriceByHotelId(hotel.getId()));
        hotelResponseDTO.setReviewCount(REVIEW_REPOSITORY.countByHotel_Id(hotel.getId()));
        hotelResponseDTO.setCategory(hotel.getCategory().getName());

        hotelResponseDTO.setFacilities(FACILITY_SERVICE.getList(hotel.getId()));

        return hotelResponseDTO;
    }
}
