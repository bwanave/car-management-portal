package com.management.car.services;

import com.management.car.models.City;
import com.management.car.models.Invoice;
import com.management.car.models.Location;
import com.management.car.models.Ride;
import com.management.car.models.accounts.RiderAccount;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public interface RideService {

    Ride bookRide(RiderAccount riderAccount, Location fromLocation, Location toLocation);

    Invoice endRide(Long rideId);

    Map<City, List<Ride>> getAllRidesPerCities(ZonedDateTime date);
}
