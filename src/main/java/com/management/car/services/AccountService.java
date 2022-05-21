package com.management.car.services;

import com.management.car.dtos.AccountRequestDto;
import com.management.car.models.accounts.Account;

public interface AccountService {

    Account createAccount(AccountRequestDto accountRequestDto);
}
