package lodgingCommander.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapDTO {
    private long id;
    private double latitude;
    private double longitude;

    public MapDTO(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
