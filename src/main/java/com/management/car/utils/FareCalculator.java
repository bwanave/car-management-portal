package com.management.car.utils;

import com.management.car.models.Ride;
import com.management.car.strategies.FareCalculationStrategy;
import jakarta.inject.Singleton;

import java.math.BigDecimal;

@Singleton
public class FareCalculator {
    public BigDecimal calculateFare(Ride ride, FareCalculationStrategy strategy) {
        return strategy.calculate(ride);
    }
}
