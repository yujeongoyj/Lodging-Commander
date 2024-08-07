package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.AddressDTO;
import com.hotel.lodgingCommander.dto.CategoryDTO;
import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.dto.RoomDTO;
import com.hotel.lodgingCommander.entity.*;
import com.hotel.lodgingCommander.repository.AddressRepository;
import com.hotel.lodgingCommander.repository.CategoryRepository;
import com.hotel.lodgingCommander.repository.HotelRepository;
import com.hotel.lodgingCommander.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {

    private final AddressRepository addressRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final CategoryRepository categoryRepository;

    public HotelService(AddressRepository addressRepository, RoomRepository roomRepository,
                        HotelRepository hotelRepository, CategoryRepository categoryRepository) {
        this.addressRepository = addressRepository;
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
        this.categoryRepository = categoryRepository;
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
                .user(user) // User 객체 설정
                .build();

        hotelRepository.save(hotel);
        return hotel.getId();
    }

    @Transactional
    public void saveRoom(RoomDTO roomDTO) {
        Hotel hotel = hotelRepository.findById(roomDTO.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));

        Room room = Room.builder()
                .name(roomDTO.getName())
                .price(roomDTO.getPrice())
                .quantity(roomDTO.getQuantity())
                .detail(roomDTO.getDetail())
                .maxPeople(roomDTO.getMaxPeople())
                .hotel(hotel)
                .build();

        roomRepository.save(room);
    }
}
