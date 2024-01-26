package com.testing.piggybank;

import com.testing.piggybank.account.AccountResponse;
import com.testing.piggybank.account.GetAccountsResponse;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Status;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.GetTransactionsResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void GetAllTransactions() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", "1");

        ResponseEntity<GetTransactionsResponse> response = restTemplate.getForEntity("/api/v1/transactions/1", GetTransactionsResponse.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(response.getBody().getTransactions().isEmpty());
    }

    @Test
    public void GetAccount() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", "1");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<GetAccountsResponse> response = restTemplate.exchange("/api/v1/accounts", HttpMethod.GET, entity, GetAccountsResponse.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(response.getBody().getAccounts().isEmpty());
    }

    @Test
    public void PostTransaction() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-User-Id", "1");

        HttpEntity<String> entitySender = new HttpEntity<>(headers);

        ResponseEntity<GetAccountsResponse> responseSender = restTemplate.exchange("/api/v1/accounts", HttpMethod.GET, entitySender, GetAccountsResponse.class);
        List<AccountResponse> senderAccount = responseSender.getBody().getAccounts();

        headers.set("X-User-Id", "2");

        HttpEntity<String> entityReciever = new HttpEntity<>(headers);

        ResponseEntity<GetAccountsResponse> responseReceiver = restTemplate.exchange("/api/v1/accounts", HttpMethod.GET, entityReciever, GetAccountsResponse.class);
        List<AccountResponse> receiverAccount = responseReceiver.getBody().getAccounts();

        Transaction transaction = new Transaction();
        transaction.setId(300);
        transaction.setDescription("Test post");
        transaction.setAmount(BigDecimal.valueOf(10));

        Instant date = new Date(2024, 01, 18).toInstant();

        transaction.setDateTime(date);
        transaction.setStatus(Status.SUCCESS);
        transaction.setCurrency(Currency.EURO);
//        transaction.setSenderAccount(senderAccount.get(0));
//        transaction.setReceiverAccount(receiverAccount.get(0));

        ResponseEntity<GetTransactionsResponse> response = restTemplate.postForEntity("/api/v1/transactions", transaction, GetTransactionsResponse.class);

        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(response.getBody().getTransactions().isEmpty());
    }
}
