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
public class AddHotelService {

    private final AddressRepository addressRepository;
    private final AddRoomRepository addRoomRepository;
    private final AddHotelRepository hotelRepository;
    private final AddCategoryRepository addCategoryRepository;
    private final AddFacilityRepository addFacilityRepository;
    private final AddImgRepository addImgRepository;

    public AddHotelService(AddressRepository addressRepository, AddRoomRepository addRoomRepository,
                           AddHotelRepository hotelRepository, AddCategoryRepository addCategoryRepository, AddFacilityRepository addFacilityRepository
    , AddImgRepository addImgRepository) {
        this.addressRepository = addressRepository;
        this.addRoomRepository = addRoomRepository;
        this.hotelRepository = hotelRepository;
        this.addCategoryRepository = addCategoryRepository;
        this.addFacilityRepository = addFacilityRepository;
        this.addImgRepository = addImgRepository;
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
        addCategoryRepository.save(category);
        return category.getId();
    }


    @Transactional
    public Long saveHotel(HotelDTO hotelDTO, User user) {
        Address address = addressRepository.findById(hotelDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        Category category = addCategoryRepository.findById(hotelDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Hotel hotel = Hotel.builder()
                .name(hotelDTO.getName())
                .address(address)
                .category(category)
                .tel(hotelDTO.getTel())
                .grade(hotelDTO.getGrade())
                .detail(hotelDTO.getDetail())
                .user(user)
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

        addFacilityRepository.save(facility);
    }

    @Transactional
    public void saveRoom(RoomDTO roomDTO) {
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Img img = null;
        if (roomDTO.getImgId() != null) {
            img = addImgRepository.findById(roomDTO.getImgId())
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

        addRoomRepository.save(room);
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
        addImgRepository.save(img);

        return img.getId();
    }
}
