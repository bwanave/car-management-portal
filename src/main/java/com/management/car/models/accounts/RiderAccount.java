package com.management.car.models.accounts;

import com.management.car.utils.AccountType;
import lombok.ToString;

@ToString
public class RiderAccount extends Account {
    public RiderAccount() {
        super(AccountType.RIDER);
    }
}
