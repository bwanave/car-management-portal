package com.management.car.services;

import com.management.car.exceptions.CityOnboardException;
import com.management.car.models.City;
import com.management.car.utils.IDGenerator;
import com.management.car.validators.Validator;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class StaticCityService implements CityService {

    private static final Map<String, City> CITY_MAP = new HashMap<>();
    private final Validator<City> validator;

    public StaticCityService(@Named("CityValidator") Validator<City> validator) {
        this.validator = validator;
    }

    @Override
    public City onboardCity(City city) {
        validator.validate(city);
        if (CITY_MAP.containsKey(city.getName())) throw new CityOnboardException("City already onboarded");
        city.setId(IDGenerator.nextValue());
        CITY_MAP.put(city.getName(), city); // In real-time, record would saved into DB
        return city;
    }

    @Override
    public List<City> getAllCities() {
        return new ArrayList<>(CITY_MAP.values());
    }
}
