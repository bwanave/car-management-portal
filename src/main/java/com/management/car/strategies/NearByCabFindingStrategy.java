package com.management.car.strategies;

import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;

import java.util.Optional;

public class NearByCabFindingStrategy implements CabFindingStrategy {

    @Override
    public Optional<Cab> findCab(Location location) {
        // Add logic using latitude and longitude
        return Optional.empty();
    }
}
