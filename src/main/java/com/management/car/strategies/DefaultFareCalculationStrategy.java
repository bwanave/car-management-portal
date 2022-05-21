package com.management.car.strategies;

import com.management.car.models.Location;
import com.management.car.models.Ride;
import jakarta.inject.Singleton;

import java.math.BigDecimal;

@Singleton
public class DefaultFareCalculationStrategy implements FareCalculationStrategy {

    @Override
    public BigDecimal calculate(Ride ride) {
        Location fromLocation = ride.getFromLocation();
        Location toLocation = ride.getToLocation();
        int distanceInKM = fromLocation.distanceInKMFrom(toLocation);
        return new BigDecimal(distanceInKM).multiply(new BigDecimal(ride.getCab().getFarePerKilometer()));
    }
}
