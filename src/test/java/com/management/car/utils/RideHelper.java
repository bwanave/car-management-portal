package com.management.car.utils;

import com.management.car.models.Location;
import com.management.car.models.Ride;
import com.management.car.models.accounts.RiderAccount;
import com.management.car.models.cabs.Cab;

import java.time.ZonedDateTime;

public class RideHelper {

    public static Ride createRide(Cab cab, Location fromLocation, Location toLocation, RiderAccount riderAccount, ZonedDateTime bookingTime) {
        Ride ride = new Ride();
        ride.setId(IDGenerator.nextValue());
        ride.setCab(cab);
        ride.setFromLocation(fromLocation);
        ride.setToLocation(toLocation);
        ride.setRiderAccount(riderAccount);
        ride.setStatus(RideStatus.COMPLETED);
        ride.setBookingTime(bookingTime);
        ride.setStartTime(ride.getBookingTime()
                              .plusMinutes(10));
        ride.setEndTime(ride.getStartTime()
                            .plusHours(3));
        return ride;
    }
}
