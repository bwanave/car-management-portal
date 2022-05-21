package com.management.car.controllers;

import com.management.car.models.City;
import com.management.car.services.CityService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;

import java.util.List;

@Controller("/api/v1/cities")
public class CityController {

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @Get
    public HttpResponse<List<City>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        return HttpResponse.ok(cities);
    }

    @Post
    public HttpResponse<City> onboardCity(City city) {
        City onboardedCity = cityService.onboardCity(city);
        return HttpResponse.created(onboardedCity);
    }
}
