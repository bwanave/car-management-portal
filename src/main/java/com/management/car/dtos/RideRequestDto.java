package com.management.car.dtos;

import com.management.car.models.Location;
import com.management.car.models.accounts.RiderAccount;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RideRequestDto {
    private RiderAccount riderAccount;
    private Location fromLocation;
    private Location toLocation;
}
