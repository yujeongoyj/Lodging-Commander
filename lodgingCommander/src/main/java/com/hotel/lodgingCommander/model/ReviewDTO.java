    package com.hotel.lodgingCommander.model;

    import lombok.Builder;
    import lombok.Data;

    import java.util.Date;

    @Data
    @Builder
    public class ReviewDTO {
        private Long id;
        private Long hotelId;
        private Long userId;
        private int rating;
        private String content;
        private Date createDate;
        private String hotelName;

        public ReviewDTO(long id, Long hotelId, Long userId, int rating, String content, Date createDate, String hotelName) {
            this.id = id;
            this.hotelId = hotelId;
            this.userId = userId;
            this.rating = rating;
            this.content = content;
            this.createDate = createDate;
            this.hotelName = hotelName;
        }
    }