package com.hotel.lodgingCommander.model.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
        import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id")
    @JsonBackReference
    private Img img;

    @Column(nullable = false)
    private int quantity;

    @Lob
    private String detail;

    @Column(name = "max_people", nullable = false)
    private int maxPeople;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Cart> carts;
}

