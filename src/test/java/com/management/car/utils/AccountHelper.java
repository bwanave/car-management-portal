package com.management.car.utils;

import com.management.car.dtos.AccountRequestDto;
import com.management.car.models.accounts.Address;
import com.management.car.models.accounts.User;

public class AccountHelper {
    public static AccountRequestDto createDriverAccountRequest(String firstName, String lastName, String city) {
        return createAccountRequest(AccountType.DRIVER, firstName, lastName, city);
    }

    public static AccountRequestDto createRiderAccountRequest(String firstName, String lastName, String city) {
        return createAccountRequest(AccountType.RIDER, firstName, lastName, city);
    }

    public static AccountRequestDto createAccountRequest(AccountType type, String firstName, String lastName, String city) {
        AccountRequestDto accountRequestDto = new AccountRequestDto();
        accountRequestDto.setUsername(firstName);
        accountRequestDto.setPassword("Test@123");
        accountRequestDto.setType(type);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(createAddress(city));
        accountRequestDto.setUser(user);
        return accountRequestDto;
    }

    private static Address createAddress(String city) {
        Address address = new Address();
        address.setCity(city);
        address.setState("Maharashtra");
        address.setCountry("India");
        address.setPinCode("411032");
        return address;
    }
}
