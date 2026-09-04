package com.ford.accountservice;

import com.ford.accountservice.entity.Account;
import com.ford.accountservice.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AccountRegistrationTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testRegisterAccount() {
        Account account = new Account();
        account.setFirstName("John");
        account.setLastName("Doe");
        account.setEmail("john.doe@example.com");
        account.setPhone("1234567890");
        account.setAddress("123 Main St");
        account.setCity("Springfield");
        account.setState("IL");
        account.setZip("62701");
        account.setCountry("USA");

        Account savedAccount = accountRepository.save(account);

        assertNotNull(savedAccount.getId());
        assertEquals("John", savedAccount.getFirstName());
        assertEquals("Doe", savedAccount.getLastName());
        assertEquals("john.doe@example.com", savedAccount.getEmail());
        assertEquals("ACTIVE", savedAccount.getStatus());
        assertNotNull(savedAccount.getCreatedDate());
        assertNotNull(savedAccount.getUpdatedDate());
    }

    @Test
    void testFindAccountByEmail() {
        Account account = new Account();
        account.setFirstName("Jane");
        account.setLastName("Smith");
        account.setEmail("jane.smith@example.com");
        accountRepository.save(account);

        assertTrue(accountRepository.existsByEmail("jane.smith@example.com"));
        assertFalse(accountRepository.existsByEmail("nonexistent@example.com"));
    }
}
