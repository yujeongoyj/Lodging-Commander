package com.hotel.lodgingCommander.service;

import com.hotel.lodgingCommander.model.bookingList.BookingListRequestModel;
import com.hotel.lodgingCommander.model.entity.BookingList;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingService {


    List<?> getAllUserBooking(Long userId);

    Boolean isCheckOutDateValid(LocalDate checkOutDate);

    Boolean createBooking(BookingListRequestModel requestDTO);

    Boolean cancelBooking(Long bookingId);

    List<?> myAllValidBooking(Long userId);

    List<?> myAllExpiredBooking(Long userId);

    List<?> myAllCancelBooking(Long userId);

}
