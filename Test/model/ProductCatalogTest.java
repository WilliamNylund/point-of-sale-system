package model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductCatalogTest {


    @Test
    void getProductByBarCode() {
        
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        testProductCatalog.getAllProducts();
        Item z = testProductCatalog.getProductByBarCode(2);
        assertTrue(z.getName().toLowerCase().equals("milk"));
    }

    @Test
    void getProductByKeyWord() {
        
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        testProductCatalog.getAllProducts();
        Item mjölk = testProductCatalog.getProductByBarCode(2);//Mjölk



        List<Item> z = testProductCatalog.getProductByKeyWord("drink");
        List<String> listStrings = new ArrayList<String>();
        listStrings.add("Leipä");
        listStrings.add("Banana");
        List<Item> breakfastList = testProductCatalog.getProductByKeyWord("breakfast");

        assertTrue(z.get(0).getName().equals(mjölk.getName()), "borde innehålla mjölk");
        List w = testProductCatalog.getProductByKeyWord("drinkerino");//Should be empty list
        assertFalse(w.contains(mjölk), "borde inte innehålla någo");
        assertTrue(listStrings.contains(breakfastList.get(0).getName()), "borde vara banana eller leipä");
        assertTrue(listStrings.contains(breakfastList.get(1).getName()), "borde vara banana eller leipä");

    }

    @Test
    void getProductByName() {
        
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        testProductCatalog.getAllProducts();
        Item leipä = testProductCatalog.getProductByName("leipä");//Bröd
        Item lleipä = testProductCatalog.getProductByName("Leipä");//Bröd
        assertTrue(lleipä.getName().equals("Leipä")); // Hittar med stor L
        assertTrue(leipä.getName().equals("Leipä")); // hittar med litet L


    }

    @Test
    void getAllProducts() {
        
        ProductCatalog testProductCatalog;
        testProductCatalog = ProductCatalog.getInstance();
        testProductCatalog.getAllProducts();
        assertTrue(testProductCatalog.getCatalog().size() == 5);//har alla 5 items som vi nu har
    }
}


