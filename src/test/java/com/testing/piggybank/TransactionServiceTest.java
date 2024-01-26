package com.testing.piggybank;

import com.testing.piggybank.account.AccountRepository;
import com.testing.piggybank.model.Account;
import com.testing.piggybank.model.Currency;
import com.testing.piggybank.model.Status;
import com.testing.piggybank.model.Transaction;
import com.testing.piggybank.transaction.TransactionRepository;
import com.testing.piggybank.transaction.TransactionService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountRepository accountRepository;
    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void FilterTransactionWithEmptyList() {
        List<Transaction> result = transactionService.filterAndLimitTransactions(Collections.emptyList(), 1L, 3);

        Assertions.assertEquals(0,result.size());
    }

    @Test
    public void FilterTransactions() {
        List<Transaction> transactions = new ArrayList<Transaction>();

        List<Account> senderAccount = accountRepository.findAllByUserId(1L);
        List<Account> receiverAccount = accountRepository.findAllByUserId(2L);

//        Account senderAccount = new Account();
//        senderAccount.setName("testSender");
//        senderAccount.setBalance(BigDecimal.valueOf(500));
//
//        Account receiverAccount = new Account();
//        receiverAccount.setName("testReceiver");
//        receiverAccount.setBalance(BigDecimal.valueOf(500));

        Transaction transaction1 = new Transaction();
        transaction1.setId(1);
//        transaction1.setSenderAccount(senderAccount);
//        transaction1.setReceiverAccount(receiverAccount);
        transaction1.setDescription("Test transaction");
        transaction1.setAmount(BigDecimal.valueOf(200));
        transaction1.setDateTime(new Date().toInstant());
        transaction1.setCurrency(Currency.EURO);
        transaction1.setStatus(Status.SUCCESS);

        Transaction transaction2 = new Transaction();
        transaction2.setId(2);
//        transaction2.setSenderAccount(senderAccount);
//        transaction2.setReceiverAccount(receiverAccount);
        transaction2.setDescription("Test transaction");
        transaction2.setAmount(BigDecimal.valueOf(200));
        transaction2.setDateTime(new Date().toInstant());
        transaction2.setCurrency(Currency.EURO);
        transaction2.setStatus(Status.SUCCESS);

        transactions.add(transaction1);
        transactions.add(transaction2);

        List<Transaction> results = transactionService.filterAndLimitTransactions(transactions, 1L, null);
    }
}
