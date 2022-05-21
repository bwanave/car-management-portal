package com.management.car.validators;

import com.management.car.models.City;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("CityValidator")
public class CityValidator implements Validator<City> {

    @Override
    public void validate(City city) {
        // Validation logic
    }
}
