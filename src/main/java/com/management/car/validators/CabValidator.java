package com.management.car.validators;

import com.management.car.models.cabs.Cab;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named("CabValidator")
public class CabValidator implements Validator<Cab> {

    @Override
    public void validate(Cab cab) {
        // Validation logic
    }
}
