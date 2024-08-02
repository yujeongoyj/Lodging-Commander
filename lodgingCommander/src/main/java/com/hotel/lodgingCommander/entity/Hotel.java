package com.hotel.lodgingCommander.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    private Facility facility;

    @Column(nullable = false, unique = true)
    private String tel;

    @Column(nullable = false)
    private int grade;

    @Lob
    private String detail;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<LikeList> likeLists;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<BookingQna> bookingQnas;

    // Getters and setters...
}
