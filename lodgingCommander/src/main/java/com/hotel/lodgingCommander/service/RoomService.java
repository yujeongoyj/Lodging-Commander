package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.RoomDTO;
import com.hotel.lodgingCommander.entity.Hotel;
import com.hotel.lodgingCommander.entity.Img;
import com.hotel.lodgingCommander.entity.Room;
import com.hotel.lodgingCommander.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public RoomDTO saveRoom(RoomDTO roomRequestDTO) {
        Room room = Room.builder()
                .name(roomRequestDTO.getName())
                .price(roomRequestDTO.getPrice())
                .hotel(Hotel.builder().id(roomRequestDTO.getHotelId()).build())
                .img(Img.builder().id(roomRequestDTO.getImgId()).build())
                .quantity(roomRequestDTO.getQuantity())
                .detail(roomRequestDTO.getDetail())
                .maxPeople(roomRequestDTO.getMaxPeople())
                .build();

        Room savedRoom = roomRepository.save(room);

        return RoomDTO.builder()
                .id(savedRoom.getId())
                .name(savedRoom.getName())
                .price(savedRoom.getPrice())
                .hotelId(savedRoom.getHotel().getId())
                .imgId(savedRoom.getImg().getId())
                .quantity(savedRoom.getQuantity())
                .detail(savedRoom.getDetail())
                .maxPeople(savedRoom.getMaxPeople())
                .build();
    }

    public RoomDTO updateRoom(Long id, RoomDTO roomRequestDTO) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.setName(roomRequestDTO.getName());
            room.setPrice(roomRequestDTO.getPrice());
            room.setHotel(Hotel.builder().id(roomRequestDTO.getHotelId()).build());
            room.setImg(Img.builder().id(roomRequestDTO.getImgId()).build());
            room.setQuantity(roomRequestDTO.getQuantity());
            room.setDetail(roomRequestDTO.getDetail());
            room.setMaxPeople(roomRequestDTO.getMaxPeople());

            Room updatedRoom = roomRepository.save(room);

            return RoomDTO.builder()
                    .id(updatedRoom.getId())
                    .name(updatedRoom.getName())
                    .price(updatedRoom.getPrice())
                    .hotelId(updatedRoom.getHotel().getId())
                    .imgId(updatedRoom.getImg().getId())
                    .quantity(updatedRoom.getQuantity())
                    .detail(updatedRoom.getDetail())
                    .maxPeople(updatedRoom.getMaxPeople())
                    .build();
        } else {
            throw new RuntimeException("Room not found");
        }
    }

    public void deleteRoom(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            roomRepository.delete(roomOptional.get());
        } else {
            throw new RuntimeException("Room not found");
        }
    }

    public RoomDTO getRoomById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            return RoomDTO.builder()
                    .id(room.getId())
                    .name(room.getName())
                    .price(room.getPrice())
                    .hotelId(room.getHotel().getId())
                    .imgId(room.getImg().getId())
                    .quantity(room.getQuantity())
                    .detail(room.getDetail())
                    .maxPeople(room.getMaxPeople())
                    .build();
        } else {
            return null;
        }
    }

    public Iterable<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream()
                .map(room -> RoomDTO.builder()
                        .id(room.getId())
                        .name(room.getName())
                        .price(room.getPrice())
                        .hotelId(room.getHotel().getId())
                        .imgId(room.getImg().getId())
                        .quantity(room.getQuantity())
                        .detail(room.getDetail())
                        .maxPeople(room.getMaxPeople())
                        .build())
                .collect(Collectors.toList());
    }
}
