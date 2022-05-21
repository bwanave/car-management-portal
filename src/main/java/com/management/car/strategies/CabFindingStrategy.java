package com.management.car.strategies;

import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;

import java.util.Optional;

public interface CabFindingStrategy {

    Optional<Cab> findCab(Location location);
}
