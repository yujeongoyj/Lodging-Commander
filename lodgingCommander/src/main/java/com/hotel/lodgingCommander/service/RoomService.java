package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.room.RoomResponseDTO;
import com.hotel.lodgingCommander.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private RoomRepository roomRepository;

    public List<RoomResponseDTO> getRoomsWithBookingStatus(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<RoomResponseDTO> roomsWithBookingStatus = roomRepository.findRoomsWithBookingStatus(hotelId, checkInDate, checkOutDate);
        return roomsWithBookingStatus;
    }
}
