package lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Facility;
import com.hotel.lodgingCommander.repository.HotelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class FacilityService {
    private HotelRepository HOTEL_REPOSITORY;

    @Transactional(readOnly = true)
    public Map<String,Boolean> getList(Long hotelId){
        Facility facility = HOTEL_REPOSITORY.findById(hotelId).get().getFacility();
        Map<String, Boolean> facilityList = new HashMap<String, Boolean>() {{
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
        return facilityList;
    }
}
