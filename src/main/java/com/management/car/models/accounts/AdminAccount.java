package com.management.car.models.accounts;

import com.management.car.utils.AccountType;
import lombok.ToString;

@ToString
public class AdminAccount extends Account {
    public AdminAccount() {
        super(AccountType.ADMIN);
    }
}
