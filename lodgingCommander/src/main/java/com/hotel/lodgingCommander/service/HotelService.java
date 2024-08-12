package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.*;
import com.hotel.lodgingCommander.entity.*;
import com.hotel.lodgingCommander.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class HotelService {

    private final AddressRepository addressRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final CategoryRepository categoryRepository;
    private final FacilityRepository facilityRepository;
    private final ImgRepository imgRepository;

    public HotelService(AddressRepository addressRepository, RoomRepository roomRepository,
                        HotelRepository hotelRepository, CategoryRepository categoryRepository, FacilityRepository facilityRepository
    , ImgRepository imgRepository) {
        this.addressRepository = addressRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.categoryRepository = categoryRepository;
        this.facilityRepository = facilityRepository;
        this.imgRepository = imgRepository;
    }

    @Transactional
    public Long saveAddress(AddressDTO addressDTO) {
        Address address = Address.builder()
                .address(addressDTO.getAddress())
                .addressDetail(addressDTO.getAddressDetail())
                .postCode(addressDTO.getPostCode())
                .latitude(addressDTO.getLatitude())
                .longitude(addressDTO.getLongitude())
                .build();
        addressRepository.save(address);
        System.out.println("AddressID: " + address.getId());
        return address.getId();
    }

    @Transactional
    public Long saveCategory(CategoryDTO categoryDTO) {
        Category category = Category.builder()
                .name(categoryDTO.getName())
                .build();
        categoryRepository.save(category);
        return category.getId();
    }


    @Transactional
    public Long saveHotel(HotelDTO hotelDTO, User user) {
        Address address = addressRepository.findById(hotelDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Category category = categoryRepository.findById(hotelDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Hotel hotel = Hotel.builder()
                .name(hotelDTO.getName())
                .address(address)
                .category(category)
                .tel(hotelDTO.getTel())
                .grade(hotelDTO.getGrade())
                .detail(hotelDTO.getDetail())
                .user(user) // 지금은 임시 User 객체 설정
                .build();

        hotelRepository.save(hotel);
        return hotel.getId();
    }

    @Transactional
    public void saveFacility(FacilityDTO facilityDTO) {
        Hotel hotel = hotelRepository.findById(facilityDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Facility facility = Facility.builder()
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

        facilityRepository.save(facility);
    }

    @Transactional
    public void saveRoom(RoomDTO roomDTO) {
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Img img = null;
        if (roomDTO.getImgId() != null) {
            img = imgRepository.findById(roomDTO.getImgId())
                    .orElseThrow(() -> new RuntimeException("Image not found"));
        }

        Room room = Room.builder()
                .name(roomDTO.getName())
                .price(roomDTO.getPrice())
                .quantity(roomDTO.getQuantity())
                .detail(roomDTO.getDetail())
                .maxPeople(roomDTO.getMaxPeople())
                .hotel(hotel)
                .img(img)
                .build();

        roomRepository.save(room);
    }

    @Transactional
    public Long saveImage(MultipartFile image) throws IOException {

        String uploadDir = "C:/path/to/upload/directory/";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(image.getInputStream(), filePath);


        Img img = Img.builder()
                .path(filePath.toString())
                .build();
        imgRepository.save(img);

        return img.getId();
    }
}
