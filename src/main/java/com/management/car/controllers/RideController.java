package com.management.car.controllers;

import com.management.car.dtos.RideRequestDto;
import com.management.car.dtos.RideResponseDto;
import com.management.car.models.Invoice;
import com.management.car.models.Ride;
import com.management.car.services.RideService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/api/v1")
public class RideController {

    private final RideService rideService;

    public RideController(RideService rideService) {
        this.rideService = rideService;
    }

    @Post("/rides/book")
    public HttpResponse<RideResponseDto> bookRide(RideRequestDto rideRequestDto) {
        Ride ride = rideService.bookRide(rideRequestDto.getRiderAccount(), rideRequestDto.getFromLocation(), rideRequestDto.getToLocation());
        RideResponseDto response = ride.toRideResponse();
        return HttpResponse.created(response);
    }

    @Post("/rides/end")
    public HttpResponse<Invoice> endRide(Long rideId) {
        Invoice invoice = rideService.endRide(rideId);
        return HttpResponse.ok(invoice);
    }
}
