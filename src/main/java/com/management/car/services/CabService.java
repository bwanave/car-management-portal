package com.management.car.services;

import com.management.car.dtos.CabDto;
import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;
import com.management.car.utils.CabStatus;

import java.util.List;

public interface CabService {

    Cab registerCab(CabDto cabDto);

    Cab updateCabLocation(String cabRegistrationNumber, Location location);

    Cab updateCabStatus(String cabRegistrationNumber, CabStatus status);

    List<Cab> getAllCabs();
}
