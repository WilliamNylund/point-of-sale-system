package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashBoxTest {

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
    void open() {
        CashBox cashBox = CashBox.getInstance();
        String status = cashBox.getStatus();
        assertTrue(status.equals("CLOSED"));
        cashBox.open();
        status = cashBox.getStatus();
        assertTrue(status.equals("OPEN"));
        assertFalse(status.equals("CLOSED"));
    }

    @Test
    void getStatus() {
    }

    @Test
    void run() {
    }
}