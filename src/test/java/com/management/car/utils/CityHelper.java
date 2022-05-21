package com.management.car.utils;

import com.management.car.models.City;

public class CityHelper {

    public static City createCity(String name) {
        City city = new City();
        city.setName(name);
        return city;
    }
}
