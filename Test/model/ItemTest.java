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

        ProductCatalog testProductCatalog = ProductCatalog.getInstance();
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

        assertFalse(testProductCatalog.getCatalog().contains("nvidia RTX3090"), "RTX3090 should not be in item list because it does not exist");
        assertFalse(testProductCatalog.getCatalog().contains(80085), "80085 should not be in item list because it does not exist");

    }

    @Test
    void verifyBestBeforeAlert() {  // BEFORE RUNNING, DISABLE THE ALERT CALL IN ADDITEM() AND CHECK THE BOOLEAN

        ProductCatalog testProductCatalog = ProductCatalog.getInstance();
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

    @Test
    void correctDiscount() {

        ProductCatalog testProductCatalog = ProductCatalog.getInstance();

        Item item = testProductCatalog.getProductByName("Milk");
        item.setPrice(3.50);
        Double priceBefore = item.getPrice();
        Double priceAfter = item.calculateDiscount(priceBefore,0.5);
        System.out.println(priceBefore + " || " + priceAfter);
        assertTrue(!priceBefore.equals(priceAfter), "The price should change");
        assertTrue(priceAfter.equals(priceBefore/2), "New price should be 1.75");

    }
}
