package com.management.car.services;

import com.management.car.dtos.CabPeakTimeDto;

import java.time.ZonedDateTime;
import java.util.List;

public interface AnalyticsService {

    List<CabPeakTimeDto> findCabPeakTimeInCities(ZonedDateTime dateTime);
}
