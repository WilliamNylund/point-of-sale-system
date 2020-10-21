package model;

import controller.CashierScreenController;
import controller.CustomerScreenController;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {

    @Test
    void getItemList() {

        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        Item testItem= testProductCatalog.getProductByBarCode(2);
        assertTrue(testTransaction.getItemList().isEmpty(), "Borde vara tomt");
        testTransaction.addItem(testItem);
        assertFalse(testTransaction.getItemList().isEmpty(), "Borde inte vara tomt");
        testTransaction.addItem(testItem);
        testTransaction.addItem(testItem);
        testTransaction.addItem(testItem);
        testTransaction.addItem(testItem);
        assertTrue(testTransaction.getItemList().size()==5, "Borde ha 5 items");
        testTransaction.removeItem(testItem);
        assertTrue(testTransaction.getItemList().size()==4, "Borde ha 4 items");
        testTransaction.removeItem(testItem);
        testTransaction.removeItem(testItem);
        testTransaction.removeItem(testItem);
        testTransaction.removeItem(testItem);
        assertTrue(testTransaction.getItemList().isEmpty(), "Borde vara tomt");
        testTransaction.removeItem(testItem);
        assertTrue(testTransaction.getItemList().isEmpty(), "Borde vara tomt");

    }



    @Test
    void getTotalCost() {

        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        Item testItem= testProductCatalog.getProductByBarCode(2);
        testTransaction.addItem(testItem);
        assertTrue(testTransaction.getTotalCost()==3.00, "värde ska vara 3.00");
        testTransaction.addItem(testItem);
        assertTrue(testTransaction.getTotalCost()==6.00, "värde ska vara 6.00");
        testTransaction.removeItem(testItem);
        testItem.setPrice(4.00);
        assertTrue(testTransaction.getTotalCost()==3.00, "värde ska vara 3.00");
        testTransaction.addItem(testItem);
        assertTrue(testTransaction.getTotalCost()==7.00, "värde ska vara 7.00");
        testTransaction.removeItem(testItem);
        assertTrue(testTransaction.getTotalCost()==3.00, "värde ska vara 3.00");


    }

    @Test
    void getOutstanding() {

        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        Item testItem= testProductCatalog.getProductByBarCode(2);
        testTransaction.addItem(testItem);
        testTransaction.addItem(testItem);
        testTransaction.setCardAmount(4.00);
        testTransaction.setCashAmount(3.00);
        assertTrue(testTransaction.getOutstanding()==0.00, "Borde vara 0.00");
    }

    @Test
    void transactionLogTest() {

        ProductCatalog testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        TransactionLog testTransactionLog = TransactionLog.getInstance();
        CustomerScreenController customerScreenController = new CustomerScreenController();
        CashierScreenController cashierScreenController = new CashierScreenController();

        Item item = testProductCatalog.getProductByBarCode(0);
        for (int i = 0; i < 5; i++){
            testTransaction.addItem(item);
        }
        assertTrue(testTransactionLog.getPausedTransactions().isEmpty());
        assertTrue(testTransaction.getItemList().size() == 5);

        testTransactionLog.getPausedTransactions().add(testTransaction);

        assertTrue(testTransactionLog.getPausedTransactions().size() == 1);

        Item item2 = testProductCatalog.getProductByName("Banana");

        Transaction testTransaction2 = new Transaction();

        testTransaction2.addItem(item2);

        testTransactionLog.getPausedTransactions().add(testTransaction2);

        assertTrue(testTransactionLog.getPausedTransactions().size() == 2);

        Transaction continuedTransaction = testTransactionLog.getPausedTransactions().get(0);
        Transaction continuedTransaction2 = testTransactionLog.getPausedTransactions().get(1);
        assertTrue(continuedTransaction.equals(testTransaction));
        assertTrue(continuedTransaction2.equals(testTransaction2));
        assertFalse(continuedTransaction.equals(testTransaction2));




    }


}