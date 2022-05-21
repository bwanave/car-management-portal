package com.management.car.services;

import com.management.car.dtos.AccountRequestDto;
import com.management.car.exceptions.AccountCreationException;
import com.management.car.models.accounts.Account;
import com.management.car.utils.IDGenerator;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class StaticAccountService implements AccountService {

    private final static Map<String, Account> ACCOUNT_MAP = new HashMap<>();

    @Override

    public Account createAccount(AccountRequestDto accountRequestDto) {
        if (ACCOUNT_MAP.containsKey(accountRequestDto.getUsername()))
            throw new AccountCreationException("Username already exist!");
        Account account = accountRequestDto.toAccount();
        account.setId(IDGenerator.nextValue());
        account.setActive(true);
        ACCOUNT_MAP.put(account.getUsername(), account);
        return account;
    }
}
