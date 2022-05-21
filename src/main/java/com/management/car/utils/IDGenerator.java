package com.management.car.utils;

import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {

    private static final AtomicLong ID = new AtomicLong();

    public static long nextValue() {
        return ID.incrementAndGet();
    }
}
