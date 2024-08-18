package lodgingCommander.dto.cart;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CartRequestDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Long roomId;
    private Long userId;
}
