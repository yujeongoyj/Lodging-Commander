package com.hotel.lodgingCommander.model.repository;

import com.hotel.lodgingCommander.model.entity.BookingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingListRepository extends JpaRepository<BookingList, Long> {
    List<BookingList> findByUserId(Long userId);
}
