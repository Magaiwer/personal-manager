package br.univates.magaiver.api.unit;

import br.univates.magaiver.domain.model.Transaction;
import br.univates.magaiver.domain.model.TransactionType;
import br.univates.magaiver.domain.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionTest {

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void shouldBeCalculateTotalExpenses() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.EXPENSE);
        transaction.setAmount(new BigDecimal("50.00"));

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.INCOME);
        transaction1.setAmount(new BigDecimal("5000.00"));

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setAmount(new BigDecimal("500.00"));

        Transaction transaction3 = new Transaction();
        transaction3.setType(TransactionType.EXPENSE);
        transaction3.setAmount(new BigDecimal("300.00"));

        Transaction transaction4 = new Transaction();
        transaction4.setType(TransactionType.EXPENSE);
        transaction4.setAmount(new BigDecimal("1250.00"));

        BigDecimal expected = new BigDecimal("2100.00");
        BigDecimal result = transaction.getTotalExpenses(
                Arrays.asList(transaction, transaction1, transaction2, transaction3, transaction4)
        );
        assertEquals(expected, result);
    }

    @Test
    public void shouldBeCalculateTotalIncome() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.INCOME);
        transaction.setAmount(new BigDecimal("500.00"));

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.INCOME);
        transaction1.setAmount(new BigDecimal("5000.00"));

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setAmount(new BigDecimal("500.00"));

        Transaction transaction3 = new Transaction();
        transaction3.setType(TransactionType.EXPENSE);
        transaction3.setAmount(new BigDecimal("300.00"));

        Transaction transaction4 = new Transaction();
        transaction4.setType(TransactionType.EXPENSE);
        transaction4.setAmount(new BigDecimal("1250.00"));

        BigDecimal expected = new BigDecimal("5500.00");
        BigDecimal result = transaction.getTotalIncome(
                Arrays.asList(transaction, transaction1, transaction2, transaction3, transaction4)
        );
        assertEquals(expected, result);
    }

    @Test
    public void shouldBeCalculateBalance() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.INCOME);
        transaction.setAmount(new BigDecimal("500.00"));

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.INCOME);
        transaction1.setAmount(new BigDecimal("5000.00"));

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setAmount(new BigDecimal("500.00"));

        Transaction transaction3 = new Transaction();
        transaction3.setType(TransactionType.EXPENSE);
        transaction3.setAmount(new BigDecimal("300.00"));

        Transaction transaction4 = new Transaction();
        transaction4.setType(TransactionType.EXPENSE);
        transaction4.setAmount(new BigDecimal("1250.00"));

        BigDecimal expected = new BigDecimal("3450.00");
        BigDecimal result = transaction.getBalance(
                Arrays.asList(transaction, transaction1, transaction2, transaction3, transaction4)
        );
        assertEquals(expected, result);
    }

    @Test
    public void shouldBeCalculateBalanceScaleUNNECESSARY() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.INCOME);
        transaction.setAmount(new BigDecimal("500.98"));

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.INCOME);
        transaction1.setAmount(new BigDecimal("5000.98"));

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.EXPENSE);
        transaction2.setAmount(new BigDecimal("500.99"));


        BigDecimal expected = new BigDecimal("5000.97");
        BigDecimal result = transaction.getBalance(
                Arrays.asList(transaction, transaction1, transaction2)
        );
        assertEquals(expected, result);
    }

    @Test
    public void shouldBeCalculateBalanceExpectedNEGATIVE() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.EXPENSE);
        transaction.setAmount(new BigDecimal("500.98"));

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.EXPENSE);
        transaction1.setAmount(new BigDecimal("5000.98"));

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.INCOME);
        transaction2.setAmount(new BigDecimal("500.99"));


        BigDecimal expected = new BigDecimal("-5000.97");
        BigDecimal result = transaction.getBalance(
                Arrays.asList(transaction, transaction1, transaction2)
        );
        assertEquals(expected, result);
    }

    @Test
    public void shouldBeCalculateBalanceWithNegativeValues() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.EXPENSE);
        transaction.setAmount(new BigDecimal("-500.98"));

        Transaction transaction1 = new Transaction();
        transaction1.setType(TransactionType.EXPENSE);
        transaction1.setAmount(new BigDecimal("-5000.98"));

        Transaction transaction2 = new Transaction();
        transaction2.setType(TransactionType.INCOME);
        transaction2.setAmount(new BigDecimal("500.99"));


        BigDecimal expected = new BigDecimal("-5000.97");
        BigDecimal result = transaction.getBalance(
                Arrays.asList(transaction, transaction1, transaction2)
        );
        assertEquals(expected, result);
    }
}
