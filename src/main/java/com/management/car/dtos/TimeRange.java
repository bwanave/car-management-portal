package com.management.car.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeRange {
    private LocalTime startTime;
    private LocalTime endTime;
}
