package com.hotel.lodgingCommander.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @OneToOne
    @JoinColumn(name = "img_id", nullable = false)
    private Img img;

    @Column(nullable = false)
    private int quantity;

    @Lob
    private String detail;

    @Column(name = "max_people", nullable = false)
    private int maxPeople;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Cart> carts;

    // Getters and setters...
}

