package com.management.car.controllers;

import com.management.car.dtos.AccountRequestDto;
import com.management.car.dtos.CabDto;
import com.management.car.dtos.RideRequestDto;
import com.management.car.dtos.RideResponseDto;
import com.management.car.models.City;
import com.management.car.models.Location;
import com.management.car.models.accounts.DriverAccount;
import com.management.car.models.accounts.RiderAccount;
import com.management.car.utils.CabHelper;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static com.management.car.utils.AccountHelper.createDriverAccountRequest;
import static com.management.car.utils.AccountHelper.createRiderAccountRequest;
import static com.management.car.utils.CityHelper.createCity;
import static io.micronaut.http.HttpRequest.POST;

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RideControllerTest {
    @Inject
    @Client("/api/v1")
    private HttpClient httpClient;
    private City pune;
    private City mumbai;
    private City delhi;
    private String hyndaiI20RegistrationNo;
    private String innovaCrystaRegistrationNo;

    @BeforeAll
    void setup() {
        // Onboard Cities
        pune = httpClient.toBlocking().exchange(POST("/cities", createCity("Pune")), City.class).body();
        mumbai = httpClient.toBlocking().exchange(POST("/cities", createCity("Mumbai")), City.class).body();
        delhi = httpClient.toBlocking().exchange(POST("/cities", createCity("Delhi")), City.class).body();

        // Register Users
        AccountRequestDto nileshDriverAccountRequestDto = createDriverAccountRequest("Nilesh", "Chopra", "Pune");
        AccountRequestDto aakashDriverAccountRequestDto = createDriverAccountRequest("Aakash", "Sharma", "Pune");
        DriverAccount nileshDriverAccount = httpClient.toBlocking().exchange(POST("/accounts", nileshDriverAccountRequestDto), DriverAccount.class).body();
        DriverAccount aakashDriverAccount = httpClient.toBlocking().exchange(POST("/accounts", aakashDriverAccountRequestDto), DriverAccount.class).body();

        // Register Cabs
        CabDto hyndaiI20Request = CabHelper.createHatchbackCabRequest("MH12FA1234", "Hyundai i10", nileshDriverAccount, pune);
        CabDto innovaCrystaRequest = CabHelper.createSuvCab("MH14FA1844", "Innova Crysta", aakashDriverAccount, pune);
        hyndaiI20RegistrationNo = httpClient.toBlocking().exchange(POST("/cabs/register", hyndaiI20Request), String.class).body();
        innovaCrystaRegistrationNo = httpClient.toBlocking().exchange(POST("/cabs/register", innovaCrystaRequest), String.class).body();
    }

    @Test
    void shouldBookRideSuccessfully() {
        // Book a ride for Rider Vinay
        AccountRequestDto riderAccountRequestDto = createRiderAccountRequest("Vinay", "Verma", "Pune");
        RiderAccount VinayRiderAccount = httpClient.toBlocking().exchange(POST("/accounts", riderAccountRequestDto), RiderAccount.class).body();
        Location fromLocation = new Location();
        fromLocation.setCity(pune);
        Location toLocation = new Location();
        toLocation.setCity(mumbai);
        RideRequestDto vinayRideRequestDto = createRideRequest(VinayRiderAccount, fromLocation, toLocation);
        RideResponseDto vinayRideResponseDto = httpClient.toBlocking().exchange(POST("/rides/book", vinayRideRequestDto), RideResponseDto.class).body();
        Assertions.assertNotNull(vinayRideResponseDto);
        Assertions.assertEquals(hyndaiI20RegistrationNo, vinayRideResponseDto.getCabRegistrationNumber());

        // Book a ride for Rider Pritam
        AccountRequestDto pritamAccountRequestDto = createRiderAccountRequest("Pritam", "Rawal", "Pune");
        RiderAccount PritamRiderAccount = httpClient.toBlocking().exchange(POST("/accounts", pritamAccountRequestDto), RiderAccount.class).body();
        RideRequestDto pritamRideRequestDto = createRideRequest(PritamRiderAccount, fromLocation, toLocation);
        RideResponseDto pritamResponse = httpClient.toBlocking().exchange(POST("/rides/book", pritamRideRequestDto), RideResponseDto.class).body();
        Assertions.assertNotNull(pritamResponse);
        Assertions.assertEquals(innovaCrystaRegistrationNo, pritamResponse.getCabRegistrationNumber());
    }

    @Test
    void shouldNotBookRide() {
        // Try to Book a ride for Rider Amit
        AccountRequestDto riderAccountRequestDto = createRiderAccountRequest("Amit", "Tomar", "Pune");
        RiderAccount riderAccount = httpClient.toBlocking().exchange(POST("/accounts", riderAccountRequestDto), RiderAccount.class).body();
        Location fromLocation = new Location();
        fromLocation.setCity(delhi);
        Location toLocation = new Location();
        toLocation.setCity(mumbai);
        RideRequestDto rideRequestDto = createRideRequest(riderAccount, fromLocation, toLocation);
        HttpClientResponseException exception = Assertions.assertThrows(HttpClientResponseException.class, () -> httpClient.toBlocking().exchange(POST("/rides/book", rideRequestDto), RideResponseDto.class).body());
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    private RideRequestDto createRideRequest(RiderAccount VinayRiderAccount, Location fromLocation, Location toLocation) {
        RideRequestDto rideRequestDto = new RideRequestDto();
        rideRequestDto.setRiderAccount(VinayRiderAccount);
        rideRequestDto.setFromLocation(fromLocation);
        rideRequestDto.setToLocation(toLocation);
        return rideRequestDto;
    }
}