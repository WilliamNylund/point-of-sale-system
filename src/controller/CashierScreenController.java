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

    private ProductCatalog productCatalog = ProductCatalog.getInstance();

    private CustomerScreenController customerScreenController;


    @FXML
    private TextField barcodeTextField;
    @FXML
    private ListView catalogListView;

    @FXML
    private ListView itemListView;

    Transaction transaction;


    @FXML
    private void initialize() {
        catalogListView.setItems(productCatalog.getCatalog());
        System.out.println("hi from cashier");
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
            Item item = productCatalog.getProductByBarCode(Integer.parseInt(barcodeTextField.getText()));
            transaction.addItem(item);
        } catch (Exception e) {
           e.printStackTrace();
        }
        /*try {
            Item item = productCatalog.getProductByName(barcodeTextField.getText());
            transaction.addItem(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            productCatalog.getProductByKeyWord(barcodeTextField.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @FXML
    private void pauseSale() throws IOException {
        System.out.println("Pause Sale");
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

    public void setTransaction(Transaction transaction){
        this.transaction = transaction;
        itemListView.setItems((ObservableList) transaction.getItemList());
    }

    @FXML
    private void addItem() {
        Item selectedItem = (Item) catalogListView.getSelectionModel().getSelectedItem();
        transaction.addItem(selectedItem);
    }

    @FXML
    private void removeItem() {
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.removeItem(selectedItem);
    }

}
