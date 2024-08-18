package lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddRoomRepository extends JpaRepository<Room, Long> {
}
