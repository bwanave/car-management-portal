package com.management.car.dtos;

import com.management.car.models.City;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CabPeakTimeDto {
    private City city;
    private TimeRange timeRang;
    private Integer bookingCount;
}
