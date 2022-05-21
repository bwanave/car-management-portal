package com.management.car.strategies;

import com.management.car.models.Ride;

import java.math.BigDecimal;

public interface FareCalculationStrategy {

    BigDecimal calculate(Ride ride);
}
