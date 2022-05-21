package com.management.car.dtos;

import com.management.car.utils.CabType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RideResponseDto {
    private long id;
    private String cabRegistrationNumber;
    private String model;
    private CabType type;
}
