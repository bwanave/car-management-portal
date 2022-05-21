package com.management.car.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@ToString
public class Location {
    private long id;
    private City city;
    private double latitude;
    private double longitude;

    public int distanceInKMFrom(Location location) {
        return ThreadLocalRandom.current().nextInt(10, 100); // In real-time, it need to calculated using latitude and longitude.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Double.compare(location.latitude, latitude) == 0
                && Double.compare(location.longitude, longitude) == 0
                && Objects.equals(city, location.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, latitude, longitude);
    }
}
