package com.management.car.utils;

import com.management.car.models.Invoice;
import com.management.car.models.Ride;
import com.management.car.strategies.DefaultFareCalculationStrategy;
import jakarta.inject.Singleton;

import java.math.BigDecimal;

@Singleton
public class InvoiceGenerator {

    private final FareCalculator fareCalculator;

    public InvoiceGenerator(FareCalculator fareCalculator) {
        this.fareCalculator = fareCalculator;
    }

    public Invoice generate(Ride ride) {
        BigDecimal totalFare = fareCalculator.calculateFare(ride, new DefaultFareCalculationStrategy());
        Invoice invoice = new Invoice();
        invoice.setId(IDGenerator.nextValue());
        invoice.setTotalAmount(totalFare);
        return invoice;
    }
}
