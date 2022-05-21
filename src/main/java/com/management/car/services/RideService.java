package com.management.car.services;

import com.management.car.models.Invoice;
import com.management.car.models.Location;
import com.management.car.models.Ride;
import com.management.car.models.accounts.RiderAccount;

public interface RideService {

    Ride bookRide(RiderAccount riderAccount, Location fromLocation, Location toLocation);

    Invoice endRide(Long rideId);
}
