package com.management.car.controllers;

import com.management.car.dtos.AccountRequestDto;
import com.management.car.dtos.CabDto;
import com.management.car.models.City;
import com.management.car.models.Location;
import com.management.car.models.accounts.DriverAccount;
import com.management.car.utils.CabHelper;
import com.management.car.utils.CabStatus;
import io.micronaut.core.type.Argument;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.Optional;

import static com.management.car.utils.AccountHelper.createDriverAccountRequest;
import static com.management.car.utils.CityHelper.createCity;
import static io.micronaut.http.HttpRequest.*;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CabControllerTest {
    @Inject
    @Client("/api/v1")
    private HttpClient httpClient;
    private City hyderabad;

    @BeforeAll
    void setup() {
        // Onboard Cities
        City chennai = httpClient.toBlocking().exchange(POST("/cities", createCity("Chennai")), City.class).body();
        City bangalore = httpClient.toBlocking().exchange(POST("/cities", createCity("Bangalore")), City.class).body();
        hyderabad = httpClient.toBlocking().exchange(POST("/cities", createCity("Hyderabad")), City.class).body();

        // Register Users
        AccountRequestDto promodDriverAccountRequestDto = createDriverAccountRequest("Pramod", "Chopra", "Chennai");
        AccountRequestDto piyushDriverAccountRequestDto = createDriverAccountRequest("Piyush", "Sharma", "Bangalore");
        DriverAccount nileshDriverAccount = httpClient.toBlocking().exchange(POST("/accounts", promodDriverAccountRequestDto), DriverAccount.class).body();
        DriverAccount aakashDriverAccount = httpClient.toBlocking().exchange(POST("/accounts", piyushDriverAccountRequestDto), DriverAccount.class).body();

        // Register Cabs
        CabDto hyndaiI20Request = CabHelper.createHatchbackCabRequest("MH12FA1223", "Hyundai i10", nileshDriverAccount, chennai);
        CabDto innovaCrystaRequest = CabHelper.createSuvCab("MH14FA1740", "Innova Crysta", aakashDriverAccount, bangalore);
        String hyndaiI20RegistrationNo = httpClient.toBlocking().exchange(POST("/cabs/register", hyndaiI20Request), String.class).body();
        String innovaCrystaRegistrationNo = httpClient.toBlocking().exchange(POST("/cabs/register", innovaCrystaRequest), String.class).body();
    }

    @Test
    void registerCabTest() {
        List<CabDto> cabs = httpClient.toBlocking().exchange(GET("/cabs"), Argument.listOf(CabDto.class)).body();
        Assertions.assertNotNull(cabs);
        Assertions.assertEquals(2, cabs.size());
    }

    @Test
    void updateLocationTest() {
        Location location = new Location();
        location.setCity(hyderabad);
        MutableHttpRequest<Object> request = PUT("/cabs/MH12FA1223/location", location);
        String registrationNumber = httpClient.toBlocking().exchange(request, String.class).body();

        List<CabDto> cabs = httpClient.toBlocking().exchange(GET("/cabs"), Argument.listOf(CabDto.class)).body();
        Assertions.assertNotNull(cabs);
        Optional<CabDto> cab = cabs.stream()
                .filter(cabDto -> cabDto.getRegistrationNumber().equals(registrationNumber))
                .findFirst();
        Assertions.assertTrue(cab.isPresent());
        Assertions.assertEquals(location, cab.get().getCurrentLocation());
    }

    @Test
    void updateStatusTest() {
        Location location = new Location();
        location.setCity(hyderabad);
        MutableHttpRequest<Object> request = PUT("/cabs/MH14FA1740/status", CabStatus.DISCONTINUED);
        String registrationNumber = httpClient.toBlocking().exchange(request, String.class).body();

        List<CabDto> cabs = httpClient.toBlocking().exchange(GET("/cabs"), Argument.listOf(CabDto.class)).body();
        Assertions.assertNotNull(cabs);
        Optional<CabDto> cab = cabs.stream()
                .filter(cabDto -> cabDto.getRegistrationNumber().equals(registrationNumber))
                .findFirst();
        Assertions.assertTrue(cab.isPresent());
        Assertions.assertEquals(CabStatus.DISCONTINUED, cab.get().getStatus());
    }
}