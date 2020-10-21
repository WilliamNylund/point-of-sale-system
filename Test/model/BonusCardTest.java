package model;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class BonusCardTest {


    private Transaction testTransaction;
    private BonusCard testBonusCard;

    @BeforeEach
    @Order(0)
    void setUp() {
        Transaction testTransaction = new Transaction();
        CustomerRegister testCustomerRegister = CustomerRegister.getInstance();
        testTransaction.setCustomer(testCustomerRegister.findByCustomerNo(101));
        testBonusCard = testTransaction.getCustomer().getBonusCard();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Order(1)
    void getNumber() {
        assertEquals(testBonusCard.getNumber(),"2");
    }

    @Test
    @Order(2)
    void setNumber() {
        assertEquals(testBonusCard.getNumber(),"2");
        testBonusCard.setNumber("1");
        assertEquals(testBonusCard.getNumber(),"1");
    }

    @Test
    @Order(3)
    void getGoodThruMonth() {
        assertEquals(testBonusCard.goodThruMonth,"4");
        assertNotEquals(testBonusCard.getGoodThruMonth(),"2");
    }

    @Test
    @Order(4)
    void setGoodThruMonth() {
        assertEquals(testBonusCard.goodThruMonth,"4");
        testBonusCard.setGoodThruMonth("2");
        assertEquals(testBonusCard.getGoodThruMonth(),"2");
    }

    @Test
    @Order(5)
    void getGoodThruYear() {
        assertEquals(testBonusCard.goodThruYear,"2023");
        assertNotEquals(testBonusCard.getGoodThruYear(),"2");
    }

    @Test
    @Order(6)
    void setGoodThruYear() {
        assertEquals(testBonusCard.goodThruYear,"2023");
        testBonusCard.setGoodThruMonth("2025");
        assertEquals(testBonusCard.getGoodThruMonth(),"2025");
    }

    @Test
    @Order(7)
    void getBlocked() {
        assertFalse(testBonusCard.getBlocked());
    }

    @Test
    @Order(8)
    void setBlocked() {
        assertFalse(testBonusCard.getBlocked());
        testBonusCard.setBlocked(true);
        assertTrue(testBonusCard.getBlocked());
    }

    @Test
    @Order(9)
    void getExpired() {
        assertFalse(testBonusCard.getExpired());
    }

    @Test
    @Order(10)
    void setExpired() {
        assertFalse(testBonusCard.getExpired());
        testBonusCard.setExpired(true);
        assertTrue(testBonusCard.getExpired());
    }

    @Test
    @Order(12)
    void getHolderName() {
        assertEquals(testBonusCard.getHolderName(),"Albin");
        assertNotEquals(testBonusCard.getHolderName(),"Alfons");
    }

    @Test
    @Order(13)
    void setHolderName() {
        assertEquals(testBonusCard.getHolderName(),"Albin");
        testBonusCard.setHolderName("Alfons");
        assertEquals(testBonusCard.getHolderName(),"Alfons");
        assertNotEquals(testBonusCard.getHolderName(),"Albin");
    }

    @Test
    @Order(14)
    void getPoints() {
        assertEquals(testBonusCard.getPoints(),0);
    }

    @Test
    @Order(15)
    void setPoints() {
        assertEquals(testBonusCard.getPoints(),0);
        testBonusCard.setPoints(420.69);
        assertEquals(testBonusCard.getPoints(),420.69);
        assertNotEquals(testBonusCard.getPoints(),0);
    }

    @Test
    @Order(16)
    void addBonusPoints() {
        Transaction testTransaction = new Transaction();
        assertEquals(testBonusCard.getPoints(),420.69);
        testTransaction.setTotalCost(1337);
        testBonusCard.addBonusPoints(testTransaction, testBonusCard.getNumber());
        assertEquals(testBonusCard.getPoints(),420.69+1337*0.05);
    }
}