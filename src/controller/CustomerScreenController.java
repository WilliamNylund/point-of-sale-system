package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.*;

import java.time.LocalDate;
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


    CashierScreenController cashierScreenController;

    Transaction transaction;
    BonusCard bonusCard;

    ProductCatalog productCatalog = ProductCatalog.getInstance();
    CardReader cardReader = CardReader.getInstance();
    CustomerRegister customerRegister = CustomerRegister.getInstance();
    TransactionLog transactionLog = TransactionLog.getInstance();
    CashBox cashbox = CashBox.getInstance();

    @FXML
    private void initialize() {
        catalogListView.setItems(productCatalog.getCatalog());
        transactionLog.createMockTransactions();
    }

    @FXML
    public void pay(){ //id: payButton
        if(transaction.getItemList().isEmpty()){
            errorMessage("No items to pay for");
            return;
        }

        if (transaction.getTotalCost() > transaction.getCardAmount() + transaction.getCashAmount()){
            errorMessage("You need to pay "  + (transaction.getTotalCost() - (transaction.getCardAmount() + transaction.getCashAmount())) + "€ more.");
            return;
        }

        if(transaction.getCardAmount() != 0.0){ //paying with card
            if(transaction.getCashAmount() == 0.0){ //paying with ONLY card
                if (transaction.getTotalCost() < transaction.getCardAmount()){
                    errorMessage("Do not donate money");
                    return;
                }
            }
            cardReader.waitForPayment(transaction.getCardAmount());
            listenForPayment();
        }
        if(transaction.getCashAmount() != 0.0){ //open cashbox if paying with cash
            if (transaction.getCardAmount() == 0.0){ //paying with ONLY cash
                finishPayment();
            }
        }
    }

    @FXML
    private void addItem() throws CloneNotSupportedException { //id: addItemButton
        if(catalogListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        Item selectedItem = (Item)catalogListView.getSelectionModel().getSelectedItem();
        Item clonedItem = (Item) selectedItem.clone();
        transaction.addItem(clonedItem);
        updateAmountFields();
    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        if (itemListView.getSelectionModel().getSelectedItem() == null){
            return;
        }

        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.removeItem(selectedItem);
        updateAmountFields();
    }

    @FXML
    private void holdTransaction(){ //id: holdTransactionButton
        cashierScreenController.startPause();
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
        cashierScreenController.getTotalTextField().setText(Double.toString(transaction.getTotalCost()));
        transaction.setOutstanding();
        outstandingTextField.setText(Double.toString(transaction.getOutstanding()));
    }

    public void listenForPayment(){
        //Listens for cardreader status to be done every second
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    if(cardReader.getStatus().equals("DONE")){
                        String[] paymentInformation = cardReader.getResult();
                        //INDEX: 0=paymentCardNumber 1 =paymentCardType 2=paymentState 3=bonusState 4=bonusCardNumber

                        transaction.setPaymentInformation(paymentInformation);
                        if(paymentCheck(paymentInformation)) {
                            cardReader.reset();
                            this.cancel();
                            finishPayment();
                        } else{
                            cardReader.reset();
                            this.cancel();
                        }
                    } else {
                        System.out.println("waiting for payment...");
                    }
                });
            }
        }, 0, 1000);

    }

    private boolean paymentCheck(String[] paymentInformation) {
        transaction.setCustomer(customerRegister.findByCustomerNo(101));
        bonusCard = transaction.getCustomer().getBonusCard();

        // paymentCardNumber[0]= behövs int testas
        // paymentCardType[1]= behvös int testas
        // paymentState[2]=null
        // bonusState[3]=ACCEPTED
        // bonusCardnumber[4]=skall finnas, bara nummror
        // Bonuscard payment
        if(paymentInformation[2]==null && paymentInformation[3].contains("ACCEPTED")&&paymentInformation[4]!=null ){//Betala med Bonus
            cashierScreenController.statusTextField.setText(transaction.getBonusState());
            bonusCard.addBonusPoints(this.transaction, transaction.getBonusCardNumber());
            return true;
        }
        // paymentCardNumber[0]= skall finnas
        // paymentCardType[1]=skall vara credit eller debit
        // paymentState[2]=Accepted
        else if(paymentInformation[0]!=null&&(paymentInformation[1].contains("CREDIT")||paymentInformation[1].contains("DEBIT"))&& paymentInformation[2].contains("ACCEPTED") ){

            // bonusState[3]=null
            // bonusCardnumber[4]=null
            //Betala med Paymentcard
            if(paymentInformation[3]==null&&paymentInformation[4]==null){
                cashierScreenController.statusTextField.setText(transaction.getPaymentState());
                return true;
            }
            // bonusState[3]=ACCEPTED
            //bonusCardnumber[4]!=null
            //Betala med Combined
            else if(paymentInformation[3].contains("ACCEPTED")&& paymentInformation[4]!=null){
                cashierScreenController.statusTextField.setText(transaction.getPaymentState());
                bonusCard.addBonusPoints(this.transaction, transaction.getBonusCardNumber());
                return true;
            }


        }

        else{
            if(paymentInformation[0]==null&& paymentInformation[1]==null){
                cashierScreenController.statusTextField.setText(paymentInformation[3]);//Bonus va problemet
            }
            else{
                cashierScreenController.statusTextField.setText(paymentInformation[2]);
            }
        }
        return false;
    }

    public void clearTextFields(){
        this.cashTextField.clear();
        this.cardTextField.clear();
        this.outstandingTextField.clear();
        this.totalTextField.clear();
        cashierScreenController.getTotalTextField().clear();
        cashierScreenController.statusTextField.clear();
        cashierScreenController.getDiscountTextField().clear();
        cashierScreenController.getChangeTextField().clear();
        cashierScreenController.getBarcodeTextField().clear();
    }

    private void finishPayment(){
        //check if cashier has to return cash
        if(transaction.getCashAmount() != 0){
            cashbox.open();
            if(transaction.getCashAmount() + transaction.getCardAmount() > transaction.getTotalCost()){
                cashierScreenController.getChangeTextField().setText(String.valueOf(Math.round(((transaction.getCardAmount() + transaction.getCashAmount()) - transaction.getTotalCost())*100.0)/100.0));
                errorMessage("You paid " + ((transaction.getCardAmount()+transaction.getCashAmount() - transaction.getTotalCost()) + "€ too much\n Returning " + ((transaction.getCardAmount()+transaction.getCashAmount()) - transaction.getTotalCost()) + "€ cash."));
            }
        }

        transaction.printReceipt();
        transaction.setPaymentDate(LocalDate.now());
        transaction.setPaid(true);
        transactionLog.getCompletedTransactions().add(transaction);
        Transaction newTransaction = new Transaction();
        this.setTransaction(newTransaction);
        cashierScreenController.setTransaction(newTransaction);
        this.clearTextFields();
    }

    public ListView getItemListView() {
        return itemListView;
    }

    public void setItemListView(ListView itemListView) {
        this.itemListView = itemListView;
    }


    public ListView getCatalogListView(){

        ListView catalogListView = this.catalogListView;
        return catalogListView;
    }

    private void errorMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
