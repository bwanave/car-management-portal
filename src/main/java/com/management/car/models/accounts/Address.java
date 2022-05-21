package com.management.car.models.accounts;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Address {
    private String city;
    private String state;
    private String country;
    private String pinCode;
}
