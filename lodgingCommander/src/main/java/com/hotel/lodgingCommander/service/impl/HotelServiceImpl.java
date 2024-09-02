package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.entity.Hotel;
import com.hotel.lodgingCommander.model.hotel.HotelRequestModel;
import com.hotel.lodgingCommander.model.hotel.HotelResponseModel;
import com.hotel.lodgingCommander.model.repository.*;
import com.hotel.lodgingCommander.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;
    private final ImgRepository imgRepository;
    private final FacilityServiceImpl facilityService;
    private final AddressRepository addressRepository;
    private final CategoryRepository categoryRepository;
    private final UserServiceImpl userService;

    public HotelResponseModel getHotelById(Long id) {
        return convertToDTO(hotelRepository.findById(id).get());
    }


    @Transactional(readOnly = true)
    public List<?> findAvailableHotels(String location, LocalDate checkInDate, LocalDate checkOutDate, int guests, int rooms) {
        int adjustedGuests = (guests % rooms == 0)
                ? guests / rooms
                : guests / rooms + 1;
        return hotelRepository.findAvailableHotels(location, checkInDate, checkOutDate, adjustedGuests, rooms).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    public Map<?, ?> getAddressByHotelId(Long hotelId) {
        var hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Hotel not found with id: " + hotelId));

        var address = hotel.getAddress();

        if (address == null) {
            throw new IllegalArgumentException("Address not found for hotel with id: " + hotelId);
        }

        var response = new HashMap<>();
        response.put("id", address.getId());
        response.put("latitude", address.getLatitude());
        response.put("longitude", address.getLongitude());

        return response;
    }

    public List<?> findByLocation(String location) {
        return hotelRepository.findByLocation(location).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<?> getRecentHotels() {
        Pageable pageable = PageRequest.of(0, 10);
        return hotelRepository.findByRecentlyList(pageable);
    }

    @Transactional
    public Boolean saveHotel(HotelRequestModel hotelDTO) {
        var address = addressRepository.findById(hotelDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        var category = categoryRepository.findById(hotelDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        var user = userService.getUserById(hotelDTO.getUserId());
        var hotel = Hotel.builder()
                .name(hotelDTO.getName())
                .address(address)
                .category(category)
                .tel(hotelDTO.getTel())
                .grade(hotelDTO.getGrade())
                .detail(hotelDTO.getDetail())
                .user(user)
                .build();

        return hotelRepository.save(hotel) != null ? true : false;
    }

    @Transactional
    public List<?> getHotelsByUserId(Long userId) {
        return hotelRepository.findByUserId(userId).stream()
                .map(hotel -> new HotelRequestModel(
                        hotel.getId(),
                        hotel.getName(),
                        userId,
                        hotel.getAddress().getId(),
                        hotel.getCategory().getId(),
                        hotel.getTel(),
                        hotel.getGrade(),
                        hotel.getDetail()))
                .collect(Collectors.toList());
    }


    public HotelResponseModel convertToDTO(Hotel hotel) {
        HotelResponseModel hotelResponseDTO = new HotelResponseModel();
        hotelResponseDTO.setId(hotel.getId());
        hotelResponseDTO.setHotelName(hotel.getName());
        hotelResponseDTO.setDetail(hotel.getDetail());
        hotelResponseDTO.setGrade(hotel.getGrade());
        hotelResponseDTO.setImgPath(imgRepository.findPathListByHotelId(hotel.getId()));
        Integer minPrice = roomRepository.findMinPriceByHotelId(hotel.getId());
        hotelResponseDTO.setMinPrice(minPrice != null ? minPrice : 0);
        hotelResponseDTO.setReviewCount(reviewRepository.countByHotel_Id(hotel.getId()));
        hotelResponseDTO.setCategory(hotel.getCategory().getName());
        hotelResponseDTO.setFacilities(facilityService.getList(hotel.getId()));

        return hotelResponseDTO;
    }

    public Map<?, ?> getHotelNamesByIds(List<Long> ids) {
        return hotelRepository.findNamesByIds(ids).stream()
                .collect(Collectors.toMap(
                        result -> (Long) result[0],
                        result -> (String) result[1]
                ));
    }
}
