package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CashierScreenController {

    @FXML private TextField barcode;

    @FXML
    private void openProductCatalog(){
        System.out.println("Open Product Catalog");
    }
    @FXML
    private void enter(){
        System.out.println(barcode.getText());
    }
    @FXML
    private void pauseSale(){
        System.out.println("Pause Sale");
    }
    @FXML
    private void addDiscount(){
        System.out.println("Add discount");
    }

    @FXML
    private void fetchCashBox() {
        try {
            Runtime.getRuntime().exec("java -jar CashBox.jar");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
