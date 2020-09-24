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

import java.io.IOException;
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
        catalogListView.setItems(productCatalog.getCatalog());
        System.out.println("hi from cust");
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
        Item selectedItem = (Item) catalogListView.getSelectionModel().getSelectedItem();
        transaction.addItem(selectedItem);
        totalTextField.setText(Double.toString(transaction.getTotalCost()));

        //refresh outstanding
        transaction.setOutstanding();
        outstandingTextField.setText(Double.toString(transaction.getOutstanding()));

    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.removeItem(selectedItem);
        totalTextField.setText(Double.toString(transaction.getTotalCost()));

        //refresh outstanding
        transaction.setOutstanding();
        outstandingTextField.setText(Double.toString(transaction.getOutstanding()));

    }

    @FXML
    private void holdTransaction(){ //id: holdTransactionButton

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
            //if empty
            if (cashTextField.getText() == null || cashTextField.getText().trim().isEmpty()){
                transaction.setCashAmount(0);
                transaction.setOutstanding();
                outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
            } else{ //not empty
                transaction.setCashAmount(Double.parseDouble(cashTextField.getText()));
                transaction.setOutstanding();
                outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
            }
        });
    }

    @FXML
    private void validateCardField() {
        cardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                cardTextField.setText(oldValue);
            }
            //if empty
            if (cardTextField.getText() == null || cardTextField.getText().trim().isEmpty()){
                transaction.setCardAmount(0);
                transaction.setOutstanding();
                outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
            } else{ //not empty
                transaction.setCardAmount(Double.parseDouble(cardTextField.getText()));
                transaction.setOutstanding();
                outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
            }

        });
    }

    public void setTransaction(Transaction transaction){
        this.transaction = transaction;
        itemListView.setItems((ObservableList) transaction.getItemList());
    }
}
