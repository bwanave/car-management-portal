package com.management.car.strategies;

import com.management.car.cache.Cache;
import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class MostIdleCabFindingStrategy implements CabFindingStrategy {

    private final Cache cache;

    public MostIdleCabFindingStrategy(Cache cache) {
        this.cache = cache;
    }

    @Override
    public Optional<Cab> findCab(Location location) {
        List<Cab> availableCabs = cache.getWaitingCabs(location.getCity());
        if (availableCabs.isEmpty())
            return Optional.empty();
        return Optional.of(availableCabs.get(0)); // Most Idle cab.
    }
}
