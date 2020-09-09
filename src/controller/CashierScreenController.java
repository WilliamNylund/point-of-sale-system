package controller;

import javafx.fxml.FXML;

import java.sql.SQLOutput;

public class CashierScreenController {

    @FXML
    private void openProductCatalog(){
        System.out.println("Open Product Catalog");
    }
    @FXML
    private void enter(){
        System.out.println("Enter");
    }
    @FXML
    private void pauseSale(){
        System.out.println("Pause Sale");
    }
    @FXML
    private void addDiscount(){
        System.out.println("Add discount");
    }
}
