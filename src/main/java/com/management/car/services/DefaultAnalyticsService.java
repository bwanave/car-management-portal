package com.management.car.services;

import com.management.car.dtos.CabPeakTimeDto;
import com.management.car.dtos.TimeRange;
import com.management.car.models.City;
import com.management.car.models.Ride;
import jakarta.inject.Singleton;

import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class DefaultAnalyticsService implements AnalyticsService {

    final private RideService rideService;

    public DefaultAnalyticsService(RideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public List<CabPeakTimeDto> findCabPeakTimeInCities(ZonedDateTime dateTime) {
        Map<City, List<Ride>> cityRidesMap = rideService.getAllRidesPerCities(dateTime);
        return cityRidesMap.keySet()
                           .stream()
                           .filter(city -> !cityRidesMap.isEmpty())
                           .map(city -> toPeakTimeDto(city, cityRidesMap.get(city)))
                           .collect(Collectors.toList());
    }

    private CabPeakTimeDto toPeakTimeDto(City city, List<Ride> rides) {
        Map<TimeRange, Integer> hourlyBookings = getHourlyBookings(rides);
        TimeRange maxBookingTimeRange = null;
        Integer maxBookingCount = null;
        for (TimeRange timeRange : hourlyBookings.keySet()) {
            int totalBookings = hourlyBookings.get(timeRange);
            if (maxBookingCount == null || totalBookings > maxBookingCount) {
                maxBookingCount = totalBookings;
                maxBookingTimeRange = timeRange;
            }
        }
        return new CabPeakTimeDto(city, maxBookingTimeRange, maxBookingCount);
    }

    private Map<TimeRange, Integer> getHourlyBookings(List<Ride> rides) {
        return rides.stream()
                    .map(Ride::getBookingTime)
                    .map(ZonedDateTime::toLocalTime)
                    .map(time -> LocalTime.of(time.getHour(), 0))
                    .map(time -> new TimeRange(time, time.plusHours(1)))
                    .collect(Collectors.toMap(timeRange -> timeRange, timeRange -> 1, (o, n) -> o + 1));
    }
}
