package com.hotel.lodgingCommander.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "checkid_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkidDate;

    @Column(name = "checkout_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;

    // Getters and setters...
}
