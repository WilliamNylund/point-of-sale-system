package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import model.ProductCatalog;
import model.Transaction;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;


public class CustomerScreenController {

    @FXML
    private TextField totalTextField;
    @FXML
    private ListView itemListView;
    @FXML
    private ListView catalogListView;
    @FXML
    private TextField outstandingTextField;
    @FXML
    public TextField cardTextField;
    @FXML
    public TextField cashTextField;
    @FXML
    private TextField bonusTextField;
    @FXML
    private CheckBox receiptCheckBox;

    CashierScreenController cashierScreenController;
    Transaction transaction;

    ProductCatalog productCatalog = ProductCatalog.getInstance();

    @FXML
    private void initialize() {
        transaction = new Transaction();
        catalogListView.setItems(productCatalog.getCatalog());
        cashierScreenController = new CashierScreenController();
    }

    @FXML
    private void pay(){ //id: payButton
        try {
            System.out.println("paying");

            if (receiptCheckBox.isSelected()) { //if receipt

            }
            Runtime.getRuntime().exec("java -jar CashBox.jar");
            URL url = new URL("http://localhost:9001/cashbox/open");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getInputStream();

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("urmmommagay");
        }
    }

    @FXML
    private void addItem(){ //id: addItemButton
        System.out.println("adding item");
        Item selectedItem = (Item) catalogListView.getSelectionModel().getSelectedItem();
        transaction.addItem(selectedItem);
        System.out.println(transaction.getItemList());

        itemListView.setItems((ObservableList) transaction.getItemList());
        totalTextField.setText(Double.toString(transaction.calculateCost(transaction.getItemList())));
    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        System.out.println("removing item");
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.getItemList().remove(selectedItem);
        System.out.println(transaction.getItemList());

        totalTextField.setText(Double.toString(transaction.calculateCost(transaction.getItemList())));
        itemListView.refresh();

    }

    @FXML
    private void holdTransaction(){ //id: holdTransactionButton
        System.out.println("holding transaction");
        productCatalog.getAllProducts();
        catalogListView.setItems(productCatalog.getCatalog());
        itemListView.setItems((ObservableList) transaction.getItemList());
    }

    @FXML
    private void continueTransaction(){ //id: continueTransactionButton
        System.out.println("continue transaction");
    }

    @FXML
    private void validateCashField() {
        cashTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                cashTextField.setText(oldValue);
            }
        });
    }

    @FXML
    private void validateCardField() {
        cardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                cardTextField.setText(oldValue);
            }
        });
    }

}
