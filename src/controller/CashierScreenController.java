package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.ProductCatalog;

import java.io.IOException;

public class CashierScreenController {

    ProductCatalog pc = new ProductCatalog();

    @FXML
    private TextField barcodeTextField;

    @FXML
    private void openProductCatalog() throws IOException {

        System.out.println("Open Product Catalog");
        Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
    }

    @FXML
    private void enter() {
        System.out.println(barcodeTextField.getText());
    }

    @FXML
    private void pauseSale() throws IOException {
        System.out.println("Pause Sale");
        //pc.findBarcode();
    }

    @FXML
    private void addDiscount() {
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

    @FXML
    private void validateBarcodeField() {
        barcodeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,2})?")) {
                barcodeTextField.setText(oldValue);
            }
        });
    }
}
