package com.management.car.models.accounts;

import com.management.car.utils.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class Account {
    private long id;
    private String username;
    private String password;
    private AccountType type;
    private User user;
    private boolean isActive;

    protected Account(AccountType type) {
        this.type = type;
    }
}
