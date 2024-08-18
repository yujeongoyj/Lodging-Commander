package lodgingCommander.dto.bookingList;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingListResponseDTO {
    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int totalPrice;
    private int totalPeople;
    private boolean cancel;

    private Long hotelId;
    private String hotelName;
    private String roomName;
    private int roomPrice;
    private String imgPath;
}
