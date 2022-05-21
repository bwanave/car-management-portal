package com.management.car.models.cabs;

import com.management.car.utils.CabType;

public class SUV extends Cab {

    public SUV() {
        super(CabType.SUV);
    }

    @Override
    public long getFarePerKilometer() {
        return 13; // In real-time, this fare need to read from DB configuration
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
