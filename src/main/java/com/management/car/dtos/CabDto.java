package com.management.car.dtos;

import com.management.car.exceptions.CabRegistrationException;
import com.management.car.models.Location;
import com.management.car.models.accounts.DriverAccount;
import com.management.car.models.cabs.Cab;
import com.management.car.models.cabs.Hatchback;
import com.management.car.models.cabs.SUV;
import com.management.car.utils.CabStatus;
import com.management.car.utils.CabType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class CabDto {
    private String registrationNumber;
    private String model;
    private CabStatus status;
    private int capacity;
    private Location currentLocation;
    private DriverAccount driverAccount;
    private ZonedDateTime registrationTime;
    private CabType type;

    public Cab toCab() {
        Cab cab;
        switch (this.type) {
            case HATCH_BACK:
                cab = new Hatchback();
                break;
            case SUV:
                cab = new SUV();
                break;
            default:
                throw new CabRegistrationException("Unsupported type");
        }
        cab.setRegistrationNumber(registrationNumber);
        cab.setModel(model);
        cab.setCapacity(capacity);
        cab.setCurrentLocation(currentLocation);
        cab.setDriverAccount(driverAccount);
        cab.setRegistrationTime(registrationTime);
        return cab;
    }
}
