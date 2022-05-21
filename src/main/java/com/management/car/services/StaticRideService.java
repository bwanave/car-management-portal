package com.management.car.services;

import com.management.car.cache.Cache;
import com.management.car.exceptions.RideBookingException;
import com.management.car.models.Invoice;
import com.management.car.models.Location;
import com.management.car.models.Ride;
import com.management.car.models.accounts.RiderAccount;
import com.management.car.models.cabs.Cab;
import com.management.car.strategies.CabFindingStrategy;
import com.management.car.strategies.MostIdleCabFindingStrategy;
import com.management.car.utils.*;
import jakarta.inject.Singleton;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class StaticRideService implements RideService {
    private static final Map<Long, Ride> RIDE_MAP = new HashMap<>();
    private final CabFinder cabFinder;
    private final Cache cache;
    private final CabService cabService;
    private final InvoiceGenerator invoiceGenerator;

    public StaticRideService(CabFinder cabFinder, Cache cache, CabService cabService, InvoiceGenerator invoiceGenerator) {
        this.cabFinder = cabFinder;
        this.cache = cache;
        this.cabService = cabService;
        this.invoiceGenerator = invoiceGenerator;
    }

    @Override
    public Ride bookRide(RiderAccount riderAccount, Location fromLocation, Location toLocation) {
        // In realtime, below strategy selection could driven from configurations
        CabFindingStrategy cabFindingStrategy = new MostIdleCabFindingStrategy(cache);
        Optional<Cab> cab = cabFinder.findCab(fromLocation, cabFindingStrategy);
        if (cab.isEmpty())
            throw new RideBookingException("Unable to book ride as no cab available");
        Ride ride = new Ride();
        ride.setId(IDGenerator.nextValue());
        ride.setCab(cab.get());
        ride.setFromLocation(fromLocation);
        ride.setToLocation(toLocation);
        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setRiderAccount(riderAccount);
        ride.setBookingTime(ZonedDateTime.now());
        ride.setStartTime(ZonedDateTime.now());
        ride.setEndTime(null);
        RIDE_MAP.put(ride.getId(), ride);
        cabService.updateCabStatus(cab.get().getRegistrationNumber(), CabStatus.ON_TRIP);
        return ride;
    }

    @Override
    public Invoice endRide(Long rideId) {
        Ride ride = RIDE_MAP.get(rideId);
        if (ride == null)
            throw new RideBookingException("Unable to end the ride. Ride missing!");
        ride.setEndTime(ZonedDateTime.now());
        ride.setStatus(RideStatus.COMPLETED);
        ride.getCab().setCurrentLocation(ride.getToLocation());
        Invoice invoice = invoiceGenerator.generate(ride);
        ride.setInvoice(invoice);
        cabService.updateCabStatus(ride.getCab().getRegistrationNumber(), CabStatus.IDLE);
        return invoice;
    }
}
