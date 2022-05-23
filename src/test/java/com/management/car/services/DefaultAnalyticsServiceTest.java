package com.management.car.services;

import com.management.car.dtos.CabPeakTimeDto;
import com.management.car.models.City;
import com.management.car.models.Location;
import com.management.car.models.Ride;
import com.management.car.utils.CityHelper;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.management.car.utils.RideHelper.createRide;
import static java.time.LocalDate.now;
import static java.time.LocalTime.of;
import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@MicronautTest
class DefaultAnalyticsServiceTest {

    @Inject
    private RideService rideService;
    @Inject
    private DefaultAnalyticsService defaultAnalyticsService;

    @Test
    void shouldFindCabPeakTimeInCities() {
        City pune = CityHelper.createCity("Pune");
        City delhi = CityHelper.createCity("Delhi");
        Mockito.when(rideService.getAllRidesPerCities(any()))
               .thenReturn(createRides(pune, delhi));
        Map<City, CabPeakTimeDto> cabPeakTimeInCities = defaultAnalyticsService.findCabPeakTimeInCities(ZonedDateTime.now())
                                                                               .stream()
                                                                               .collect(Collectors.toMap(CabPeakTimeDto::getCity, Function.identity()));
        System.out.println(cabPeakTimeInCities);
        Assertions.assertEquals(2, cabPeakTimeInCities.size());
        Assertions.assertEquals(12, cabPeakTimeInCities.get(pune)
                                                       .getTimeRang()
                                                       .getStartTime()
                                                       .getHour());
        Assertions.assertEquals(13, cabPeakTimeInCities.get(pune)
                                                       .getTimeRang()
                                                       .getEndTime()
                                                       .getHour());
        Assertions.assertEquals(4, cabPeakTimeInCities.get(pune)
                                                      .getBookingCount());
        Assertions.assertEquals(10, cabPeakTimeInCities.get(delhi)
                                                       .getTimeRang()
                                                       .getStartTime()
                                                       .getHour());
        Assertions.assertEquals(11, cabPeakTimeInCities.get(delhi)
                                                       .getTimeRang()
                                                       .getEndTime()
                                                       .getHour());
        Assertions.assertEquals(5, cabPeakTimeInCities.get(delhi)
                                                      .getBookingCount());
    }

    private Map<City, List<Ride>> createRides(City pune, City delhi) {
        Map<City, List<Ride>> rideMap = new HashMap<>();
        Location puneLocation = new Location();
        puneLocation.setCity(pune);
        rideMap.put(puneLocation.getCity(), new ArrayList<>());
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(9, 10), systemDefault())));
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(10, 10), systemDefault())));
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(12, 10), systemDefault())));
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(12, 30), systemDefault())));
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(12, 35), systemDefault())));
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(12, 40), systemDefault())));
        rideMap.get(puneLocation.getCity())
               .add(createRide(null, puneLocation, null, null, of(now(), of(15, 0), systemDefault())));

        Location delhiLocation = new Location();
        delhiLocation.setCity(delhi);
        rideMap.put(delhiLocation.getCity(), new ArrayList<>());
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(7, 10), systemDefault())));
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(10, 10), systemDefault())));
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(10, 10), systemDefault())));
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(10, 30), systemDefault())));
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(10, 35), systemDefault())));
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(10, 40), systemDefault())));
        rideMap.get(delhiLocation.getCity())
               .add(createRide(null, delhiLocation, null, null, of(now(), of(21, 0), systemDefault())));
        return rideMap;
    }

    @MockBean(StaticRideService.class)
    public RideService dependency() {
        return mock(RideService.class);
    }
}
