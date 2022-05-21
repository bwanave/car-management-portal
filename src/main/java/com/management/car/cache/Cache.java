package com.management.car.cache;

import com.management.car.models.City;
import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;
import com.management.car.utils.CabStatus;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Singleton
public class Cache {
    private static final Map<String, List<Cab>> WAITING_CAB_MAP = new HashMap<>();

    public void add(Cab cab) {
        List<Cab> queue = WAITING_CAB_MAP.getOrDefault(cab.getCurrentLocation().getCity().getName(), new LinkedList<>());
        queue.add(cab);
        WAITING_CAB_MAP.put(cab.getCurrentLocation().getCity().getName(), queue);
    }

    public void remove(Cab cab, Location location) {
        List<Cab> oldLocationQueue = WAITING_CAB_MAP.getOrDefault(location.getCity().getName(), new LinkedList<>());
        oldLocationQueue.remove(cab);
        WAITING_CAB_MAP.put(location.getCity().getName(), oldLocationQueue);
    }

    public List<Cab> getWaitingCabs(City city) {
        return WAITING_CAB_MAP.getOrDefault(city.getName(), new LinkedList<>());
    }

    public void update(Cab cab, CabStatus status) {
        switch (status) {
            case IDLE:
                this.add(cab);
                break;
            case ON_TRIP:
            case LOST:
            case NOT_AVAILABLE:
            case DISCONTINUED:
                this.remove(cab, cab.getCurrentLocation());
        }
    }
}
