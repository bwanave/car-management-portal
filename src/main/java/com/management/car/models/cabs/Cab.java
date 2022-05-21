package com.management.car.models.cabs;

import com.management.car.dtos.CabDto;
import com.management.car.models.Location;
import com.management.car.models.accounts.DriverAccount;
import com.management.car.utils.CabStatus;
import com.management.car.utils.CabType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public abstract class Cab {
    private String registrationNumber;
    private String model;
    private CabStatus status;
    private int capacity;
    private Location currentLocation;
    private DriverAccount driverAccount;
    private ZonedDateTime registrationTime;
    private CabType type;

    protected Cab(CabType type) {
        this.type = type;
    }


    public abstract long getFarePerKilometer();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cab cab = (Cab) o;
        return Objects.equals(registrationNumber, cab.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }

    public CabDto toCabDto() {
        CabDto cabDto = new CabDto();
        cabDto.setRegistrationNumber(registrationNumber);
        cabDto.setModel(model);
        cabDto.setCapacity(capacity);
        cabDto.setStatus(status);
        cabDto.setCurrentLocation(currentLocation);
        cabDto.setDriverAccount(driverAccount);
        cabDto.setRegistrationTime(registrationTime);
        return cabDto;
    }
}



