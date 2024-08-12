package com.hotel.lodgingCommander.sercvice;

import com.hotel.lodgingCommander.entity.Facility;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.model.hotel.HotelResponseDTO;
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
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;
    private ReviewRepository reviewRepository;
    private ImgRepository imgRepository;

    @Transactional(readOnly = true)
    public List<HotelResponseDTO> findAvailableHotels(String location, LocalDate checkInDate, LocalDate checkOutDate, int guests, int rooms) {
        List<Hotel> availableHotels = hotelRepository.findAvailableHotels(location, checkInDate, checkOutDate, guests, rooms);
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
        hotelResponseDTO.setImgPath(imgRepository.findPathListByHotelId(hotel.getId()));
        hotelResponseDTO.setMinPrice(roomRepository.findMinPriceByHotelId(hotel.getId()));
        hotelResponseDTO.setReviewCount(reviewRepository.countByHotel_Id(hotel.getId()));
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

        return hotelResponseDTO;
    }
}
