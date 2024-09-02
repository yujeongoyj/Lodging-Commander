package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.dto.room.RoomResponseDTO;
import com.hotel.lodgingCommander.entity.Room;
import com.hotel.lodgingCommander.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;

    private RoomResponseDTO convertToDTO(Room entity) {
        RoomResponseDTO resultDTO = new RoomResponseDTO();
        resultDTO.setId(entity.getId());
        resultDTO.setHotelName(entity.getHotel().getName());
        resultDTO.setRoomName(entity.getName());
        resultDTO.setPrice(entity.getPrice());
        resultDTO.setDetail(entity.getDetail());
        resultDTO.setMaxPeople(entity.getMaxPeople());
        resultDTO.setImgPath(entity.getImg().getPath());
        resultDTO.setHotelId(entity.getHotel().getId());
        return resultDTO;
    }

    public List<RoomResponseDTO> getRoomsWithBookingStatus(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<RoomResponseDTO> roomsWithBookingStatus = roomRepository.findRoomsWithBookingStatus(hotelId, checkInDate, checkOutDate);
        return roomsWithBookingStatus;
    }

    @Transactional(readOnly = true)
    public RoomResponseDTO selectOneRoom(Long id) {
        Room room = roomRepository.findRoomById(id);
        return convertToDTO(room);
    }

}
