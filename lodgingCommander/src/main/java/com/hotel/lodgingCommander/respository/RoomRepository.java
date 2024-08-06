package com.hotel.lodgingCommander.respository;

import com.hotel.lodgingCommander.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
