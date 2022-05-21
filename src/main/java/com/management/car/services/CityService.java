package com.management.car.services;

import com.management.car.models.City;

import java.util.List;

public interface CityService {

    City onboardCity(City city);

    List<City> getAllCities();
}
