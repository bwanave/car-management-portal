package com.management.car.controllers;

import com.management.car.dtos.AccountRequestDto;
import com.management.car.models.accounts.Account;
import com.management.car.services.AccountService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

@Controller("/api/v1")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Post("/accounts")
    public HttpResponse<Account> createAccount(AccountRequestDto accountRequestDto) {
        Account createdAccount = accountService.createAccount(accountRequestDto);
        return HttpResponse.created(createdAccount);
    }
}
