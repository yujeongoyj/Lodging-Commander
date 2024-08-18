package lodgingCommander.repository;

import com.hotel.lodgingCommander.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddHotelRepository extends JpaRepository<Hotel, Long> {}