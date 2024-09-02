package com.hotel.lodgingCommander.service.impl;

import com.hotel.lodgingCommander.model.bookingList.BookingListRequestModel;
import com.hotel.lodgingCommander.model.bookingList.BookingListResponseModel;
import com.hotel.lodgingCommander.model.entity.BookingList;
import com.hotel.lodgingCommander.model.repository.BookingListRepository;
import com.hotel.lodgingCommander.model.repository.CartRepository;
import com.hotel.lodgingCommander.model.repository.RoomRepository;
import com.hotel.lodgingCommander.model.repository.UserRepository;
import com.hotel.lodgingCommander.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingListRepository bookingListRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final RoomRepository roomRepository;
    private final FacilityServiceImpl facilityService;

    private BookingListResponseModel convertToDTO(BookingList entity) {
        BookingListResponseModel resultDTO = new BookingListResponseModel();
        resultDTO.setId(entity.getId());
        resultDTO.setCheckInDate(entity.getCheckInDate());
        resultDTO.setCheckOutDate(entity.getCheckOutDate());
        resultDTO.setTotalPrice(entity.getTotalPrice());
        resultDTO.setTotalPeople(entity.getTotalPeople());
        resultDTO.setCancel(entity.getCancel());

        resultDTO.setHotelId(entity.getRoom().getHotel().getId());
        resultDTO.setHotelName(entity.getRoom().getHotel().getName());
        resultDTO.setRoomName(entity.getRoom().getName());
        resultDTO.setRoomPrice(entity.getRoom().getPrice());
        resultDTO.setImgPath(entity.getRoom().getImg().getPath());
        return resultDTO;
    }

    public List<?> getAllUserBooking(Long userId) {
        return bookingListRepository.findByUserId(userId);
    }

    public Boolean isCheckOutDateValid(LocalDate checkOutDate) {
        LocalDate currentDate = LocalDate.now();
        return !checkOutDate.isBefore(currentDate);
    }

    @Transactional
    public Boolean createBooking(BookingListRequestModel requestDTO) {
        var user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));
        var room = roomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Room Not Found"));

        var bookingListEntity = BookingList.builder()
                .user(user)
                .room(room)
                .checkInDate(requestDTO.getCheckInDate())
                .checkOutDate(requestDTO.getCheckOutDate())
                .totalPeople(requestDTO.getTotalPeople())
                .totalPrice(requestDTO.getTotalPrice())
                .cancel(false)
                .build();
        try {
            bookingListRepository.save(bookingListEntity);
            cartRepository.deleteById(requestDTO.getCartId());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public Boolean cancelBooking(Long bookingId) {
        try {
            var booking = bookingListRepository.findById(bookingId)
                    .orElseThrow(() -> new EntityNotFoundException("Booking Not Found"));
            booking.setCancel(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<?> myAllValidBooking(Long userId) {
        return bookingListRepository.findByUserId(userId).stream()
                .filter(booking -> isCheckOutDateValid(booking.getCheckOutDate()) && !booking.getCancel())
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<?> myAllExpiredBooking(Long userId) {
        return bookingListRepository.findByUserId(userId).stream()
                .filter(booking -> !isCheckOutDateValid(booking.getCheckOutDate()) && !booking.getCancel())
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<?> myAllCancelBooking(Long userId) {
        return bookingListRepository.findByUserId(userId).stream()
                .filter(booking -> booking.getCancel())
                .map(this::convertToDTO)
                .toList();
    }

    public Map<?, ?> showBookingPage(Long roomId) {
        return new HashMap<>() {{
            put("room", roomRepository.findRoomById(roomId));
            put("FacilityList", facilityService.getList(roomRepository.findRoomById(roomId).getHotel().getId()));
        }};
    }
}
