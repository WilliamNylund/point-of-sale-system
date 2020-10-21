package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerRegisterTest {

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
    void findByCustomerNo() {
        CustomerRegister customerRegister = CustomerRegister.getInstance();
        Customer customer = customerRegister.findByCustomerNo(101);

        assertTrue(customer.getCustomerNo() == 101);
        assertTrue(customer.getFirstName().equals("Albin"));
        assertTrue(customer.getSex().equals("MALE"));
    }

    @Test
    void findByCustomerBonusCard() {
        CustomerRegister customerRegister = CustomerRegister.getInstance();
        Customer customer = customerRegister.findByCustomerBonusCard(2, 2023, 4 );

        assertTrue(customer.getCustomerNo() == 101);
        assertTrue(customer.getFirstName().equals("Albin"));
        assertTrue(customer.getSex().equals("MALE"));
        assertFalse(customer.getSex().equals("FEMALE"));
        assertFalse(customer.getCustomerNo() == 420);

    }
}