package com.management.car.utils;

public enum CabStatus {
    /**
     * Cab is IDLE currently and ready to accept the next Trip
     */
    IDLE,

    /**
     * Cab is not available as Trip is in progress
     */
    ON_TRIP,

    /**
     * Cab is not available as it has been lost
     */
    LOST,

    /**
     * Cab is not available temporarily as Driver is off or maintenance problem, etc.
     */
    NOT_AVAILABLE,

    /**
     * Cab service is discontinued (deleted)
     */
    DISCONTINUED
}