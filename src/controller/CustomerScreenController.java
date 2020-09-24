package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.CardReader;
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
    CardReader cardReader = CardReader.getInstance();

    @FXML
    private void initialize() {
        catalogListView.setItems(productCatalog.getCatalog());
    }

    @FXML
    private void pay(){ //id: payButton

        if (transaction.getTotalCost() > transaction.getCardAmount() + transaction.getCashAmount()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pay up bitch");
            alert.setHeaderText(null);
            alert.setContentText("Your poor ass need to pay "  + (transaction.getTotalCost() - (transaction.getCardAmount() + transaction.getCashAmount())) + "€ more, bitch.");
            alert.showAndWait();
            return;
        }
        if (transaction.getTotalCost() < transaction.getCardAmount() + transaction.getCashAmount()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("I aint taking no charity");
            alert.setHeaderText(null);
            alert.setContentText("you tried to donate "  + ((transaction.getCardAmount() + transaction.getCashAmount()) - transaction.getTotalCost()) + "€ , bitch.");
            alert.showAndWait();
            return;
        }

        if(transaction.getCardAmount() != 0.0){ //paying with card
            //cardReader.run();
            cardReader.waitForPayment(transaction.getCardAmount());


        }

        if (receiptCheckBox.isSelected()) { //if receipt

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
        cardReader.getResult();
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
