package com.ford.accountservice.controller;

import com.ford.accountservice.entity.Account;
import com.ford.accountservice.request.AccountRegistrationRequest;
import com.ford.accountservice.response.AccountRegistrationResponse;
import com.ford.accountservice.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<AccountRegistrationResponse> registerAccount(@Valid @RequestBody AccountRegistrationRequest request) {
        try {
            Account account = convertToEntity(request);
            Account registeredAccount = accountService.registerAccount(account);
            AccountRegistrationResponse response = convertToResponse(registeredAccount);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AccountRegistrationResponse>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();
        List<AccountRegistrationResponse> responses = accounts.stream()
                .map(this::convertToResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountRegistrationResponse> getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id)
                .map(account -> ResponseEntity.ok(convertToResponse(account)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AccountRegistrationResponse> getAccountByEmail(@PathVariable String email) {
        return accountService.getAccountByEmail(email)
                .map(account -> ResponseEntity.ok(convertToResponse(account)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountRegistrationResponse> updateAccount(@PathVariable Long id, @Valid @RequestBody AccountRegistrationRequest request) {
        try {
            Account account = convertToEntity(request);
            Account updatedAccount = accountService.updateAccount(id, account);
            AccountRegistrationResponse response = convertToResponse(updatedAccount);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Account convertToEntity(AccountRegistrationRequest request) {
        Account account = new Account();
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setAddress(request.getAddress());
        account.setCity(request.getCity());
        account.setState(request.getState());
        account.setZip(request.getZip());
        account.setCountry(request.getCountry());
        return account;
    }

    private AccountRegistrationResponse convertToResponse(Account account) {
        AccountRegistrationResponse response = new AccountRegistrationResponse();
        response.setId(account.getId());
        response.setFirstName(account.getFirstName());
        response.setLastName(account.getLastName());
        response.setEmail(account.getEmail());
        response.setPhone(account.getPhone());
        response.setAddress(account.getAddress());
        response.setCity(account.getCity());
        response.setState(account.getState());
        response.setZip(account.getZip());
        response.setCountry(account.getCountry());
        response.setStatus(account.getStatus());
        response.setCreatedDate(account.getCreatedDate());
        response.setUpdatedDate(account.getUpdatedDate());
        return response;
    }
}
