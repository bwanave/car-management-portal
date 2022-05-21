package com.management.car.controllers;

import com.management.car.dtos.CabDto;
import com.management.car.models.Location;
import com.management.car.models.cabs.Cab;
import com.management.car.services.CabService;
import com.management.car.utils.CabStatus;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller("/api/v1/cabs")
public class CabController {

    private final CabService cabService;

    public CabController(CabService cabService) {
        this.cabService = cabService;
    }

    @Get
    public HttpResponse<List<CabDto>> getAllCabs() {
        List<CabDto> cabs = cabService.getAllCabs()
                .stream()
                .map(Cab::toCabDto)
                .collect(Collectors.toList());
        return HttpResponse.ok(cabs);
    }

    @Post("/register")
    public HttpResponse<String> registerCab(CabDto cabDto) {
        Cab registerCab = cabService.registerCab(cabDto);
        return HttpResponse.created(registerCab.getRegistrationNumber());
    }

    @Put("/{cabRegistrationNumber}/location")
    public HttpResponse<String> updateCabLocation(@PathVariable String cabRegistrationNumber, Location location) {
        Cab updatedCab = cabService.updateCabLocation(cabRegistrationNumber, location);
        return HttpResponse.ok(updatedCab.getRegistrationNumber());
    }

    @Put("/{cabRegistrationNumber}/status")
    public HttpResponse<String> updateCabStatus(@PathVariable String cabRegistrationNumber, CabStatus status) {
        Cab updatedCab = cabService.updateCabStatus(cabRegistrationNumber, status);
        return HttpResponse.ok(updatedCab.getRegistrationNumber());
    }
}
