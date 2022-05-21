package com.management.car.models;

import com.management.car.dtos.RideResponseDto;
import com.management.car.models.accounts.RiderAccount;
import com.management.car.models.cabs.Cab;
import com.management.car.utils.RideStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class Ride {
    private long id;
    private Cab cab;
    private Location fromLocation;
    private Location toLocation;
    private RiderAccount riderAccount;
    private RideStatus status;
    private ZonedDateTime bookingTime;
    private ZonedDateTime startTime;
    private ZonedDateTime endTime;
    private Invoice invoice;

    public RideResponseDto toRideResponse() {
        RideResponseDto response = new RideResponseDto();
        response.setId(id);
        response.setCabRegistrationNumber(this.cab.getRegistrationNumber());
        response.setModel(this.cab.getModel());
        response.setType(this.cab.getType());
        return response;
    }
}
