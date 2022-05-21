package com.management.car.models.accounts;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private long id;
    private String firstName;
    private String lastName;
    private Address address;
}
