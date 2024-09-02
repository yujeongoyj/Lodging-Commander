package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.entity.Img;
import com.hotel.lodgingCommander.model.entity.Room;
import com.hotel.lodgingCommander.model.repository.HotelRepository;
import com.hotel.lodgingCommander.model.repository.ImgRepository;
import com.hotel.lodgingCommander.model.repository.RoomRepository;
import com.hotel.lodgingCommander.model.room.RoomRequestModel;
import com.hotel.lodgingCommander.model.room.RoomResponseModel;
import com.hotel.lodgingCommander.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ImgRepository imgRepository;

    private RoomResponseModel convertToDTO(Room entity) {
        return RoomResponseModel.builder()
                .id(entity.getId())
                .hotelName(entity.getHotel().getName())
                .roomName(entity.getName())
                .price(entity.getPrice())
                .detail(entity.getDetail())
                .maxPeople(entity.getMaxPeople())
                .imgPath(entity.getImg().getPath())
                .hotelId(entity.getHotel().getId())
                .build();
    }


    public List<?> getRoomsWithBookingStatus(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        return roomRepository.findRoomsWithBookingStatus(hotelId, checkInDate, checkOutDate);
    }

    @Transactional(readOnly = true)
    public RoomResponseModel selectOneRoom(Long id) {
        return convertToDTO(roomRepository.findRoomById(id));
    }

    @Transactional
    public Boolean saveRoom(RoomRequestModel roomDTO) {
        var hotel = hotelRepository.findById(roomDTO.getHotelId())
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

        return roomRepository.save(room) != null ? true : false;
    }
}
