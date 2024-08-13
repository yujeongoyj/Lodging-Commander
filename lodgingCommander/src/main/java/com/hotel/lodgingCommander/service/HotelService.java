package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Facility;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.dto.hotel.HotelResponseDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.ImgRepository;
import com.hotel.lodgingCommander.repository.ReviewRepository;
import com.hotel.lodgingCommander.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HotelService {
    private HotelRepository HOTEL_REPOSITORY;
    private RoomRepository ROOM_REPOSITORY;
    private ReviewRepository REVIEW_REPOSITORY;
    private ImgRepository IMG_REPOSITORY;

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
    public MapDTO getAddressByHotelId(Long hotelId) {
        Hotel hotel = HOTEL_REPOSITORY.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId));
        Address address = hotel.getAddress(); // Hotel의 Address 가져오기

        if (address == null) {
            throw new IllegalArgumentException("Address not found for hotel with id: " + hotelId);
        }

        return new MapDTO(address.getId(), address.getLatitude(), address.getLongitude());
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

        Facility facility = hotel.getFacility();
        hotelResponseDTO.setFacilities(new HashMap<String, Boolean>() {{
            put("에어컨", facility.getAirConditioning());
            put("공항 셔틀", facility.getAirportShuttle());
            put("바", facility.getBar());
            put("조식", facility.getBreakfast());
            put("전기차 충전소", facility.getEvChargingStation());
            put("무료 주차", facility.getFreeParking());
            put("헬스장", facility.getGym());
            put("세탁 시설", facility.getLaundryFacilities());
            put("금연", facility.getNonSmoking());
            put("반려동물 동반 가능", facility.getPetFriendly());
            put("레스토랑", facility.getRestaurant());
            put("스파", facility.getSpa());
            put("수영장", facility.getSwimmingPool());
            put("24시간 프론트 데스크", facility.getTwentyFourHourFrontDesk());
        }});

    }
}