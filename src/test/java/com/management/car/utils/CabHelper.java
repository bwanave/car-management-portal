package com.management.car.utils;

import com.management.car.dtos.CabDto;
import com.management.car.models.City;
import com.management.car.models.Location;
import com.management.car.models.accounts.DriverAccount;

public class CabHelper {

    public static CabDto createHatchbackCabRequest(String registrationNumber, String model, DriverAccount driverAccount, City city) {
        return createCab(CabType.HATCH_BACK, registrationNumber, model, driverAccount, city);
    }

    public static CabDto createSuvCab(String registrationNumber, String model, DriverAccount driverAccount, City city) {
        return createCab(CabType.SUV, registrationNumber, model, driverAccount, city);
    }

    public static CabDto createCab(CabType type, String registrationNumber, String model, DriverAccount driverAccount, City city) {
        CabDto cabDto = new CabDto();
        cabDto.setRegistrationNumber(registrationNumber);
        cabDto.setModel(model);
        cabDto.setCapacity(4);
        cabDto.setCurrentLocation(createLocation(city));
        cabDto.setDriverAccount(driverAccount);
        cabDto.setType(type);
        return cabDto;
    }

    private static Location createLocation(City city) {
        Location location = new Location();
        location.setCity(city);
        return location;
    }
}
