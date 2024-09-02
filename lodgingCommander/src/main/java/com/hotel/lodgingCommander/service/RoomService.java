package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.room.RoomRequestModel;
import com.hotel.lodgingCommander.model.room.RoomResponseModel;

import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    List<?> getRoomsWithBookingStatus(Long hotelId, LocalDate checkInDate, LocalDate checkOutDate);

    RoomResponseModel selectOneRoom(Long id);

    Boolean saveRoom(RoomRequestModel roomDTO);
}
