package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.*;

import java.time.LocalDate;
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Add some items");
            alert.showAndWait();
            return;
        }

        if (transaction.getTotalCost() > transaction.getCardAmount() + transaction.getCashAmount()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pay up bitch");
            alert.setHeaderText(null);
            alert.setContentText("Your poor ass need to pay "  + (transaction.getTotalCost() - (transaction.getCardAmount() + transaction.getCashAmount())) + "€ more, bitch.");
            alert.showAndWait();
            return;
        }
        if (transaction.getTotalCost() < transaction.getCardAmount() + transaction.getCashAmount()){
            cashierScreenController.getChangeTextField().setText(String.valueOf((transaction.getCardAmount() + transaction.getCashAmount()) - transaction.getTotalCost()));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("I aint taking no charity");
            alert.setHeaderText(null);
            alert.setContentText("you tried to donate "  + ((transaction.getCardAmount() + transaction.getCashAmount()) - transaction.getTotalCost()) + "€ , bitch.");
            alert.showAndWait();
            return;
        }

        if(transaction.getCardAmount() != 0.0){ //paying with card
            cardReader.waitForPayment(transaction.getCardAmount());
            listenForPayment();
        }
        if(transaction.getCashAmount() != 0.0){ //open cashbox if paying with cash
            cashbox.open();
            finishPayment();

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
    private void continueTransaction(){ //id: continueTransactionButton
        System.out.println("this button should be removed");
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
        cashierScreenController.getTotalTextField().setText(Double.toString(transaction.getTotalCost()));
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
                        String[] paymentInformation = cardReader.getResult();
                        //INDEX: 0=paymentCardNumber 1 =paymentCardType 2=paymentState 3=bonusState 4=bonusCardNumber

                        transaction.setPaymentInformation(paymentInformation);
                        if(paymentCheck(paymentInformation)) {
                            this.cancel();
                            finishPayment();
                        }
                        this.cancel();
                        cardReader.reset();

                    } else {
                        System.out.println("waiting for payment...");
                    }

                });
            }
        }, 0, 1000);

    }

    private boolean paymentCheck(String[] paymentInformation) {

        // paymentCardNumber[0]= behövs int testas
        // paymentCardType[1]= behvös int testas
        // paymentState[2]=null
        // bonusState[3]=ACCEPTED
        // bonusCardnumber[4]=skall finnas, bara nummror
        // Bonuscard payment
        if(paymentInformation[2]==null && paymentInformation[3].contains("ACCEPTED")&&paymentInformation[4]!=null ){//Betala med Bonus
            System.out.println("Betala med Bonus kort");
            cashierScreenController.statusTextField.setText(transaction.getBonusState());
            return true;
        }
        // paymentCardNumber[0]= skall finnas
        // paymentCardType[1]=skall vara credit eller debit
        // paymentState[2]=Accepted
        else if(paymentInformation[0]!=null&&(paymentInformation[1].contains("CREDIT")||paymentInformation[1].contains("DEBIT"))&& paymentInformation[2].contains("ACCEPTED") ){
            System.out.println("GOT HERE");

            // bonusState[3]=null
            // bonusCardnumber[4]=null
            //Betala med Paymentcard
            if(paymentInformation[3]==null&&paymentInformation[4]==null){
                System.out.println("Betala med Paymentcard");
                cashierScreenController.statusTextField.setText(transaction.getPaymentState());
                return true;
            }
            // bonusState[3]=ACCEPTED
            //bonusCardnumber[4]!=null
            //Betala med Combined
            else if(paymentInformation[3].contains("ACCEPTED")&& paymentInformation[4]!=null){
                System.out.println("Betala med Combined");
                cashierScreenController.statusTextField.setText(transaction.getPaymentState());
                return true;
            }


        }

        else{
            System.out.println("TRANSACTION FAILED");
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
        transaction.printReceipt();
        //bonusCard = new BonusCard();
        //Customer customer = CustomerRegister.getInstance().findByCustomerBonusCard(Integer.parseInt(transaction.getBonusCardNumber()),2023,4);
        Customer customer = transaction.getCustomer();
        System.out.println(customer.getCustomerNo());
        System.out.println(customer.getFirstName());
        System.out.println(customer.getLastName());
        System.out.println(customer.getBirthDate());
        System.out.println(customer.getSex());
        System.out.println(customer.getStreetAddress());
        System.out.println(customer.getBonusCard().getNumber());
        System.out.println(customer.getBonusCard().getGoodThruMonth());
        System.out.println(customer.getBonusCard().getGoodThruYear());
        System.out.println(customer.getBonusCard().getHolderName());
        System.out.println(customer.getAge());
        bonusCard = transaction.getCustomer().getBonusCard();
        bonusCard.addBonusPoints(this.transaction, transaction.getBonusCardNumber());
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
}
