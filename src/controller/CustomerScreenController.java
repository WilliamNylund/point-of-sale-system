package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.*;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


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
    CustomerRegister customerRegister = CustomerRegister.getInstance();
    TransactionLog transactionLog = TransactionLog.getInstance();

    HashMap<String, Integer> soldProducts = new HashMap<>(); // TODO

    @FXML
    private void initialize() {
        catalogListView.setItems(productCatalog.getCatalog());
    }

    @FXML
    public void pay(){ //id: payButton

        if (transaction.getTotalCost() > transaction.getCardAmount() + transaction.getCashAmount()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pay up bitch");
            alert.setHeaderText(null);
            alert.setContentText("Your poor ass need to pay "  + (transaction.getTotalCost() - (transaction.getCardAmount() + transaction.getCashAmount())) + "€ more, bitch.");
            alert.showAndWait();
            return;
        }
        if (transaction.getTotalCost() < transaction.getCardAmount() + transaction.getCashAmount()){
            cashierScreenController.changeTextField.setText(String.valueOf((transaction.getCardAmount() + transaction.getCashAmount()) - transaction.getTotalCost()));
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
            listenForPayment();
        } else{
            finishPayment();

        }

        soldProducts.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
    }

    @FXML
    private void addItem(){ //id: addItemButton
        Item selectedItem = (Item) catalogListView.getSelectionModel().getSelectedItem();
        transaction.addItem(selectedItem);
        updateAmountFields();

        int count = soldProducts.containsKey(selectedItem.toString()) ? soldProducts.get(selectedItem.toString()) : 0;
        soldProducts.put(selectedItem.toString(), count + 1);
    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.removeItem(selectedItem);
        updateAmountFields();

        int count = soldProducts.containsKey(selectedItem.toString()) ? soldProducts.get(selectedItem.toString()) : 0;
        soldProducts.put(selectedItem.toString(), count - 1);
    }

    @FXML
    private void holdTransaction(){ //id: holdTransactionButton
        cashierScreenController.startPause();
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
            //if CardTextField is empty
            if (cardTextField.getText() == null || cardTextField.getText().trim().isEmpty()){
                transaction.setCardAmount(0);
                transaction.setOutstanding();
                outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
            } else{
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

    public void setCashierScreenController(CashierScreenController cashierScreenController){
        this.cashierScreenController = cashierScreenController;
    }

    public void updateAmountFields(){
        totalTextField.setText(Double.toString(transaction.getTotalCost()));
        cashierScreenController.totalTextField.setText(Double.toString(transaction.getTotalCost()));
        transaction.setOutstanding();
        outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
    }

    public void listenForPayment(){
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    if(cardReader.getStatus().equals("DONE")){
                        System.out.println("paid");

                        String[] paymentInformation = cardReader.getResult();
                        //INDEX: 0 => paymentCardNumber 1 => paymentCardType 2 => paymentState 3=> bonusState 4=> bonusCardNumber

                        transaction.setPaymentInformation(paymentInformation);
                        finishPayment();

                        cardReader.reset();
                        this.cancel();

                    } else {
                        System.out.println("waiting for payment...");
                    }

                });
            }
        }, 0, 1000);

    }

    public void clearTextFields(){
        this.cashTextField.setText("");
        this.cardTextField.setText("");
        this.outstandingTextField.setText("");
        this.totalTextField.setText("");
        cashierScreenController.getTotalTextField().setText("");
    }

    private void finishPayment(){
        transactionLog.getCompletedTransactions().add(transaction);
        Transaction newTransaction = new Transaction();
        this.setTransaction(newTransaction);
        cashierScreenController.setTransaction(newTransaction);
        this.clearTextFields();

        if (receiptCheckBox.isSelected()) { //if receipt

        }
    }
}
