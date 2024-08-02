package com.hotel.lodgingCommander.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "booking_list")
public class BookingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column
    private Boolean checkIn;

    @Column(nullable = false)
    private LocalDateTime checkInDate;

    @Column(nullable = false)
    private LocalDateTime checkOutDate;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private int totalPeople;

    // Getters and Setters
}
