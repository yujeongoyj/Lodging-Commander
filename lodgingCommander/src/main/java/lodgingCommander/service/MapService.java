package lodgingCommander.service;

import com.hotel.lodgingCommander.entity.Address;
import com.hotel.lodgingCommander.model.MapDTO;
import com.hotel.lodgingCommander.repository.MapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapService {

    private final MapRepository mapRepository;

    @Autowired
    public MapService(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public MapDTO getLocationById(Long id) {
        Address address = mapRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid location Id: " + id));
        return new MapDTO(address.getId(), address.getLatitude(), address.getLongitude());
    }
}