package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionTest {

    @Test
    void getItemList() {
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        Item testItem= testProductCatalog.getProductByBarCode(69);
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
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        Item testItem= testProductCatalog.getProductByBarCode(69);
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
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();
        Item testItem= testProductCatalog.getProductByBarCode(69);
        testTransaction.addItem(testItem);
        testTransaction.addItem(testItem);
        testTransaction.setCardAmount(4.00);
        testTransaction.setCashAmount(3.00);
        assertTrue(testTransaction.getOutstanding()==0.00, "Borde vara 0.00");
    }

    @Test
    void setOutstanding() {
    }



    @Test
    void addItem() {
    }

    @Test
    void removeItem() {
    }


}