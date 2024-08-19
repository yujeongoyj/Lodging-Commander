    package com.hotel.lodgingCommander.model;

    import lombok.Builder;
    import lombok.Data;

    import java.util.Date;

    @Data
    public class ReviewDTO {
        private Long id;
        private Long hotelId;
        private Long userId;
        private String hotelName;
        private int rating;
        private String content;
        private Date createDate;

        public ReviewDTO(long id, Long hotelId, String hotelName,Long userId, int rating, String content, Date createDate) {
            this.id = id;
            this.hotelId = hotelId;
            this.hotelName= hotelName;
            this.userId = userId;
            this.rating = rating;
            this.content = content;
            this.createDate = createDate;
        }
    }