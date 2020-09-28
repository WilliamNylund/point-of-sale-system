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
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog TestproductCatalog;
        TestproductCatalog = ProductCatalog.getInstance();
        TestproductCatalog.getAllProducts();
        Item z = TestproductCatalog.getProductByBarCode(69);
        try {
            Item x = TestproductCatalog.getProductByBarCode(69);
        } catch (Exception E) {
            System.out.println("PROBLEM");
        }

        assertTrue(z.getName().equals("Dildo"), "Borde hitta Didlo");
        assertTrue(z.getPrice() == 3.00, "Borde vara 3.00");
        assertFalse(z.getPrice() == 3.10, "Borde inte 3.10");

    }

    @Test
    void getProductByKeyWord() {
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog TestproductCatalog;
        TestproductCatalog = ProductCatalog.getInstance();
        TestproductCatalog.getAllProducts();
        Item mjölk = TestproductCatalog.getProductByBarCode(2);//Mjölk



        Item y = TestproductCatalog.getProductByBarCode(69);//Dildo
        List<Item> z = TestproductCatalog.getProductByKeyWord("drink");
        List<String> listStrings = new ArrayList<String>();
        listStrings.add("Leipä");
        listStrings.add("Banana");
        List<Item> breakfastList = TestproductCatalog.getProductByKeyWord("breakfast");


        assertFalse(z.contains(y), "borde inte innehålla dildo");
        assertTrue(z.get(0).getName().equals(mjölk.getName()), "borde innehålla mjölk");
        List w = TestproductCatalog.getProductByKeyWord("drinkerino");//Should be empty list
        assertFalse(w.contains(mjölk), "borde inte innehålla någo");
        assertTrue(listStrings.contains(breakfastList.get(0).getName()), "borde vara banana eller leipä");
        assertTrue(listStrings.contains(breakfastList.get(1).getName()), "borde vara banana eller leipä");

    }

    @Test
    void getProductByName() {
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog TestproductCatalog;
        TestproductCatalog = ProductCatalog.getInstance();
        TestproductCatalog.getAllProducts();
        Item leipä = TestproductCatalog.getProductByName("leipä");//Bröd
        Item lleipä = TestproductCatalog.getProductByName("Leipä");//Bröd
        assertTrue(lleipä.getName().equals("Leipä")); // Hittar med stor L
        assertTrue(leipä.getName().equals("Leipä")); // hittar med litet L


    }

    @Test
    void getAllProducts() {
        try {
            Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProductCatalog TestproductCatalog;
        TestproductCatalog = ProductCatalog.getInstance();
        TestproductCatalog.getAllProducts();
        assertTrue(TestproductCatalog.getCatalog().size() == 5);//har alla 5 items som vi nu har
    }
}


