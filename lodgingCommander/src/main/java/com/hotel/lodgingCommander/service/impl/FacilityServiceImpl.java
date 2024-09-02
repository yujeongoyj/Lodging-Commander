package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.FacilityModel;
import com.hotel.lodgingCommander.model.entity.Facility;
import com.hotel.lodgingCommander.model.repository.FacilityRepository;
import com.hotel.lodgingCommander.model.repository.HotelRepository;
import com.hotel.lodgingCommander.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final HotelRepository hotelRepository;
    private final FacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public Map<String, Boolean> getList(Long hotelId) {
        var facility = hotelRepository.findById(hotelId).get().getFacility();
        return new HashMap<String, Boolean>() {{
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
        }};
    }

    @Transactional
    public Boolean save(FacilityModel facilityDTO, Long hotelId) {
        facilityDTO.setHotelId(hotelId);
        var hotel = hotelRepository.findById(facilityDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        var facility = Facility.builder()
                .hotel(hotel)
                .freeWifi(facilityDTO.getFreeWifi())
                .nonSmoking(facilityDTO.getNonSmoking())
                .airConditioning(facilityDTO.getAirConditioning())
                .laundryFacilities(facilityDTO.getLaundryFacilities())
                .freeParking(facilityDTO.getFreeParking())
                .twentyFourHourFrontDesk(facilityDTO.getTwentyFourHourFrontDesk())
                .breakfast(facilityDTO.getBreakfast())
                .airportShuttle(facilityDTO.getAirportShuttle())
                .spa(facilityDTO.getSpa())
                .bar(facilityDTO.getBar())
                .swimmingPool(facilityDTO.getSwimmingPool())
                .gym(facilityDTO.getGym())
                .evChargingStation(facilityDTO.getEvChargingStation())
                .petFriendly(facilityDTO.getPetFriendly())
                .restaurant(facilityDTO.getRestaurant())
                .build();

        return facilityRepository.save(facility) != null ? true : false;
    }
}
