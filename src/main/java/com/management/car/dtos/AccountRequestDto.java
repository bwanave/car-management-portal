package com.management.car.dtos;

import com.management.car.exceptions.AccountCreationException;
import com.management.car.models.accounts.*;
import com.management.car.utils.AccountType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AccountRequestDto {
    private String username;
    private String password;
    private AccountType type;
    private User user;

    public Account toAccount() {
        Account account;
        switch (this.getType()) {
            case ADMIN:
                account = new AdminAccount();
                break;
            case RIDER:
                account = new RiderAccount();
                break;
            case DRIVER:
                account = new DriverAccount();
                break;
            default:
                throw new AccountCreationException("Unsupported account type");
        }
        account.setUsername(this.getUsername());
        account.setPassword(this.getPassword());
        account.setUser(this.getUser());
        return account;
    }
}
