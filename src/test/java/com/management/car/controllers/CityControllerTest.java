package com.management.car.controllers;

import com.management.car.models.City;
import io.micronaut.core.type.Argument;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static com.management.car.utils.CityHelper.createCity;
import static io.micronaut.http.HttpRequest.GET;
import static io.micronaut.http.HttpRequest.POST;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CityControllerTest {
    @Inject
    @Client("/api/v1")
    private HttpClient httpClient;

    @Test
    void setup() {
        City nagpur = httpClient.toBlocking().exchange(POST("/cities", createCity("Nagpur")), City.class).body();
        List<City> cities = httpClient.toBlocking().exchange(GET("/cities"), Argument.listOf(City.class)).body();
        Assertions.assertNotNull(cities);
        Assertions.assertTrue(cities.size() >= 1);

        int prev = cities.size();
        City solapur = httpClient.toBlocking().exchange(POST("/cities", createCity("Solapur")), City.class).body();
        cities = httpClient.toBlocking().exchange(GET("/cities"), Argument.listOf(City.class)).body();
        Assertions.assertNotNull(cities);
        Assertions.assertEquals(prev + 1, cities.size());
    }
}