package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import model.ProductCatalog;
import model.Transaction;

import java.io.IOException;

public class CashierScreenController {

    ProductCatalog productCatalog = ProductCatalog.getInstance();


    @FXML
    private TextField barcodeTextField;
    @FXML
    private ListView catalogListView;

    @FXML
    private ListView itemListView;

    Transaction transaction; //transaction is a big questionmark atm


    @FXML
    private void initialize() {
        transaction = new Transaction();
        catalogListView.setItems(productCatalog.getCatalog());
        itemListView.setItems((ObservableList) transaction.getItemList());

    }

    @FXML
    private void openProductCatalog() throws IOException {

        System.out.println("Open Product Catalog");
        Runtime.getRuntime().exec("java -jar ProductCatalog.jar");
    }

    @FXML
    private void searchItem() {
        System.out.println(barcodeTextField.getText());
        try {
            productCatalog.getProductByBarCode(Integer.parseInt(barcodeTextField.getText()));
            catalogListView.setItems(productCatalog.getCatalog());
        } catch (Exception e) {
           e.printStackTrace();
        }
        try {
            productCatalog.getProductByName(barcodeTextField.getText());
            catalogListView.setItems(productCatalog.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            productCatalog.getProductByKeyWord(barcodeTextField.getText());
            catalogListView.setItems(productCatalog.getCatalog());
        } catch (Exception e) {
            e.printStackTrace();
        }
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

}
