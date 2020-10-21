package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionLogTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getInstance() {
    }

    @Test
    void getPausedTransactions() {
    }

    @Test
    void setPausedTransactions() {
    }

    @Test
    void getCompletedTransactions() {
    }

    @Test
    void setCompletedTransactions() {
    }

    @Test
    void createMockTransactions() {
    }

    @Test
    void getProductsSoldByDate() {
        TransactionLog transactionLog = TransactionLog.getInstance();
        System.out.println(transactionLog.getCompletedTransactions().size());
    }

    @Test
    void getProductsSoldByCustomer() {

    }

    @Test
    void sortByValue() {
    }
}