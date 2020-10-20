package model;

import controller.CashierScreenController;
import controller.CustomerScreenController;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemTest {

    @Test
    void addItemAndRemoveItem() {

        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();

        Item testItem = testProductCatalog.getProductByName("Banana");
        testTransaction.addItem(testItem);
        assertTrue(testTransaction.getItemList().contains(testItem), "Banana should be in item list");

        Item testItem2 = testProductCatalog.getProductByBarCode(1);
        testTransaction.addItem(testItem2);
        assertTrue(testTransaction.getItemList().contains(testItem2), "Item with barcode 1 should be in item list");

        testTransaction.removeItem(testItem);
        assertFalse(testTransaction.getItemList().contains(testItem), "Banana should be removed from item list");
        assertTrue(testTransaction.getItemList().contains(testItem2), "Item with barcode 1 should be in item list");

    }

    @Test
    void verifyBestBeforeAlert() {  // BEFORE RUNNING, DISABLE THE ALERT CALL IN ADDITEM() AND CHECK THE BOOLEAN

        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        Transaction testTransaction = new Transaction();

        Item oldItem = testProductCatalog.getProductByName("Milk");
        oldItem.setBestBefore(LocalDate.now().plusDays(1));
        System.out.println(LocalDate.now().plusDays(1));
        testTransaction.addItem(oldItem);
        assertTrue(testTransaction.testInfoMessage == true, "The BBE alert should have been called");

        Item newItem = testProductCatalog.getProductByName("Banana");
        newItem.setBestBefore(LocalDate.now().plusDays(3));
        System.out.println(LocalDate.now().plusDays(3));
        testTransaction.addItem(newItem);
        assertTrue(testTransaction.testInfoMessage == false, "The BBE alert should --NOT-- have been called");
    }

}
