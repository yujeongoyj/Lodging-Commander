package com.hotel.lodgingCommander.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacilityModel {
    private Long hotelId;
    private Boolean freeWifi;
    private Boolean nonSmoking;
    private Boolean airConditioning;
    private Boolean laundryFacilities;
    private Boolean freeParking;
    private Boolean twentyFourHourFrontDesk;
    private Boolean breakfast;
    private Boolean airportShuttle;
    private Boolean spa;
    private Boolean bar;
    private Boolean swimmingPool;
    private Boolean gym;
    private Boolean evChargingStation;
    private Boolean petFriendly;
    private Boolean restaurant;
}
