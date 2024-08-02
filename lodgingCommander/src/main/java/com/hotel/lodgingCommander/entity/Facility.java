package com.hotel.lodgingCommander.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facilities")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "free_wifi")
    private Boolean freeWifi;

    @Column(name = "non_smoking")
    private Boolean nonSmoking;

    @Column(name = "air_conditioning")
    private Boolean airConditioning;

    @Column(name = "laundry_facilities")
    private Boolean laundryFacilities;

    @Column(name = "free_parking")
    private Boolean freeParking;

    @Column(name = "twentyfour_hour_front_desk")
    private Boolean twentyFourHourFrontDesk;

    @Column(name = "breakfast")
    private Boolean breakfast;

    @Column(name = "airport_shuttle")
    private Boolean airportShuttle;

    @Column(name = "spa")
    private Boolean spa;

    @Column(name = "bar")
    private Boolean bar;

    @Column(name = "swimming_pool")
    private Boolean swimmingPool;

    @Column(name = "gym")
    private Boolean gym;

    @Column(name = "ev_charging_station")
    private Boolean evChargingStation;

    @Column(name = "pet_friendly")
    private Boolean petFriendly;

    @Column(name = "restaurant")
    private Boolean restaurant;
}