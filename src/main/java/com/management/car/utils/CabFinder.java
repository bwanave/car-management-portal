package com.management.car.utils;

import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;
import com.management.car.strategies.CabFindingStrategy;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class CabFinder {
    public Optional<Cab> findCab(Location location, CabFindingStrategy strategy) {
        return strategy.findCab(location);
    }
}
