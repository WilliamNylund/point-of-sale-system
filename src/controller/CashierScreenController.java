package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.awt.event.ActionEvent;
import java.sql.SQLOutput;

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
            System.out.println("open cashbox");
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../view/CashBox.fxml"));
            Parent par = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("CashBox");
            stage.setScene(new Scene(par));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
