package com.management.car.services;

import com.management.car.cache.Cache;
import com.management.car.dtos.CabDto;
import com.management.car.exceptions.CabRegistrationException;
import com.management.car.exceptions.CabUpdateException;
import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;
import com.management.car.utils.CabStatus;
import com.management.car.validators.Validator;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class StaticCabService implements CabService {

    private static final Map<String, Cab> CAB_MAP = new HashMap<>();
    private final Validator<Cab> validator;
    private final Cache cache;
    private final AccountService accountService;

    public StaticCabService(@Named("CabValidator") Validator<Cab> validator, Cache cache, AccountService accountService) {
        this.validator = validator;
        this.cache = cache;
        this.accountService = accountService;
    }

    @Override
    public Cab registerCab(CabDto cabDto) {
        Cab cab = cabDto.toCab();
        validator.validate(cab);
        if (CAB_MAP.containsKey(cab.getRegistrationNumber()))
            throw new CabRegistrationException("Cab already registered");
        cab.setStatus(CabStatus.IDLE);
        cab.setRegistrationTime(ZonedDateTime.now());
        CAB_MAP.put(cab.getRegistrationNumber(), cab); // In real-time, record would saved into DB
        cache.add(cab);
        return cab;
    }

    @Override
    public Cab updateCabLocation(String cabRegistrationNumber, Location location) {
        if (!CAB_MAP.containsKey(cabRegistrationNumber))
            throw new CabUpdateException("Unable to update the location as Cab is not registered");
        Cab cab = CAB_MAP.get(cabRegistrationNumber);
        Location oldLocation = cab.getCurrentLocation();
        cab.setCurrentLocation(location);

        cache.remove(cab, oldLocation);
        cache.add(cab);
        return cab;
    }

    @Override
    public Cab updateCabStatus(String cabRegistrationNumber, CabStatus status) {
        if (!CAB_MAP.containsKey(cabRegistrationNumber))
            throw new CabUpdateException("Unable to update the status as Cab is not registered");
        Cab cab = CAB_MAP.get(cabRegistrationNumber);
        cab.setStatus(status);
        cache.update(cab, status);
        return cab;
    }

    @Override
    public List<Cab> getAllCabs() {
        return new ArrayList<>(CAB_MAP.values());
    }
}
