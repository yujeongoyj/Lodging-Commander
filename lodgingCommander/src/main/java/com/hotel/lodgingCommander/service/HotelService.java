/*
package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.dto.HotelDTO;
import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.entity.Category;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.User;
import com.hotel.lodgingCommander.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    public HotelDTO saveHotel(HotelDTO hotelRequestDTO) {
        Hotel hotel = Hotel.builder()
                .name(hotelRequestDTO.getName())
                .user(User.builder().id(hotelRequestDTO.getUserId()).build()) // User 객체를 UserDTO로 바꿔서 사용하면 좋습니다.
                .address(Address.builder().id(hotelRequestDTO.getAddressId()).build()) // Address 객체를 AddressDTO로 바꿔서 사용하면 좋습니다.
                .category(Category.builder().id(hotelRequestDTO.getCategoryId()).build()) // Category 객체를 CategoryDTO로 바꿔서 사용하면 좋습니다.
                .tel(hotelRequestDTO.getTel())
                .grade(hotelRequestDTO.getGrade())
                .detail(hotelRequestDTO.getDetail())
                .build();

        Hotel savedHotel = hotelRepository.save(hotel);

        return HotelDTO.builder()
                .id(savedHotel.getId())
                .name(savedHotel.getName())
                .userId(savedHotel.getUser().getId())
                .addressId(savedHotel.getAddress().getId())
                .categoryId(savedHotel.getCategory().getId())
                .tel(savedHotel.getTel())
                .grade(savedHotel.getGrade())
                .detail(savedHotel.getDetail())
                .build();
    }

    public HotelDTO updateHotel(Long id, HotelDTO hotelRequestDTO) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            hotel.setName(hotelRequestDTO.getName());
            hotel.setUser(User.builder().id(hotelRequestDTO.getUserId()).build());
            hotel.setAddress(Address.builder().id(hotelRequestDTO.getAddressId()).build());
            hotel.setCategory(Category.builder().id(hotelRequestDTO.getCategoryId()).build());
            hotel.setTel(hotelRequestDTO.getTel());
            hotel.setGrade(hotelRequestDTO.getGrade());
            hotel.setDetail(hotelRequestDTO.getDetail());

            Hotel updatedHotel = hotelRepository.save(hotel);

            return HotelDTO.builder()
                    .id(updatedHotel.getId())
                    .name(updatedHotel.getName())
                    .userId(updatedHotel.getUser().getId())
                    .addressId(updatedHotel.getAddress().getId())
                    .categoryId(updatedHotel.getCategory().getId())
                    .tel(updatedHotel.getTel())
                    .grade(updatedHotel.getGrade())
                    .detail(updatedHotel.getDetail())
                    .build();
        } else {
            throw new RuntimeException("Hotel not found");
        }
    }

    public void deleteHotel(Long id) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(id);
        if (hotelOptional.isPresent()) {
            hotelRepository.delete(hotelOptional.get());
        } else {
            throw new RuntimeException("Hotel not found");
        }
    }
}
*/
