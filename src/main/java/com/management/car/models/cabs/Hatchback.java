package com.management.car.models.cabs;

import com.management.car.utils.CabType;

public class Hatchback extends Cab {

    public Hatchback() {
        super(CabType.HATCH_BACK);
    }

    @Override
    public long getFarePerKilometer() {
        return 9; // In real-time, this fare need to read from DB configuration
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
