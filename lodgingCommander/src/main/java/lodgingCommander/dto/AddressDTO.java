package lodgingCommander.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String address;
    private String addressDetail;
    private String postCode;
    private double latitude;
    private double longitude;  // 나중에 지도 API로 대체 가능
}
