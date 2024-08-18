package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.hotel.HotelResponseDTO;
import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.model.MapDTO;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.ImgRepository;
import com.hotel.lodgingCommander.repository.ReviewRepository;
import com.hotel.lodgingCommander.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public HotelResponseDTO getHotelById(Long id) {
        return convertToDTO(HOTEL_REPOSITORY.findById(id).orElseThrow(() -> new RuntimeException("Hotel not found")));
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

    public List<HotelResponseDTO> findByLocation(String location) {
        List<Hotel> byLocation = HOTEL_REPOSITORY.findByLocation(location);
        return byLocation.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Hotel> getRecentHotels() {
        Pageable pageable = PageRequest.of(0, 10); // Page 0, size 10
        return HOTEL_REPOSITORY.findByRecentlyList(pageable);
    }

    private HotelResponseDTO convertToDTO(Hotel hotel) {
        HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
        hotelResponseDTO.setId(hotel.getId());
        hotelResponseDTO.setHotelName(hotel.getName());
        hotelResponseDTO.setDetail(hotel.getDetail());
        hotelResponseDTO.setGrade(hotel.getGrade());
        hotelResponseDTO.setImgPath(IMG_REPOSITORY.findPathListByHotelId(hotel.getId()));
        Integer minPrice = ROOM_REPOSITORY.findMinPriceByHotelId(hotel.getId());
        hotelResponseDTO.setMinPrice(minPrice != null ? minPrice : 0);
        hotelResponseDTO.setReviewCount(REVIEW_REPOSITORY.countByHotel_Id(hotel.getId()));
        hotelResponseDTO.setCategory(hotel.getCategory().getName());
        hotelResponseDTO.setFacilities(FACILITY_SERVICE.getList(hotel.getId()));

        return hotelResponseDTO;
    }
}
