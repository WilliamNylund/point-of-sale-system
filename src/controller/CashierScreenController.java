package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Optional;

public class CashierScreenController {

    private ProductCatalog productCatalog = ProductCatalog.getInstance();
    private TransactionLog transactionLog = TransactionLog.getInstance();

    private CustomerScreenController customerScreenController;

    @FXML
    private TextField barcodeTextField;

    @FXML
    private TextField totalTextField;

    @FXML
    private TextField changeTextField;

    @FXML
    private TextField discountTextField;

    @FXML
    private ListView catalogListView;

    @FXML
    private ListView itemListView;

    @FXML
    private ComboBox transactionComboBox;
    @FXML
    public TextField statusTextField;

    Transaction transaction;

    private boolean boolPass;

    @FXML
    private void initialize() {
        catalogListView.setItems(productCatalog.getCatalog());
        transactionComboBox.setItems(transactionLog.getPausedTransactions());
    }

    @FXML
    private void openAdminWindow() {    // Opens window for sale & marketing related activities
        passwordAlert();
        if (boolPass) {
            try {
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/AdminScreen.fxml"));


                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setTitle("Administrator");
                stage.setResizable(false);
                stage.setScene(scene);
                AdminScreenController adminScreenController = loader.getController();
                adminScreenController.setcustomerScreenController(this.customerScreenController);

                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void searchItem() {     // Utilizes try/catch to find product, returns an alert if no match found

        int itemsBefore = itemListView.getItems().size();
        //System.out.println(barcodeTextField.getText());   // FOR DEBUGGING
        if (barcodeTextField.getText().isEmpty()) {
            errorMessage("' " + "'" + " did not match any items\nPlease try another search term");
        } else {
            try {
                Item item= productCatalog.getProductByNameFromLocal(barcodeTextField.getText().trim());
                if (item != null) {
                    transaction.addItem(item);
                    customerScreenController.updateAmountFields();
                }

            } catch (Exception e) {

            }
            try {
                Item item = productCatalog.getProductByBarCodeFromLocal(Integer.parseInt(barcodeTextField.getText().trim()));
                if (item != null) {
                    transaction.addItem(item);
                    customerScreenController.updateAmountFields();
                }

            } catch (Exception e) {

            }

            int itemsAfter = itemListView.getItems().size();

            if (itemsBefore == itemsAfter) {
                errorMessage("'" + barcodeTextField.getText() + "'" + " did not match any items\nPlease try another search term");
            }

        /*try {
            List item = productCatalog.getProductByKeyWord((barcodeTextField.getText()));
            System.out.println(item);
        } catch (Exception e) {
            System.out.println("sum ting wong");
        }
    }
        }*/
        }
    }

    @FXML
    private void pauseTransaction() {
        System.out.println("Pause transaction");

        if(transaction.getItemList().isEmpty()){
           errorMessage("Transaction is empty!\nOnly ongoing sales can be paused");
            return;
        }

        transactionLog.getPausedTransactions().add(transaction);
        Transaction newTransaction = new Transaction();
        this.setTransaction(newTransaction);
        customerScreenController.setTransaction(newTransaction);
        customerScreenController.clearTextFields();
    }

    @FXML
    private void continueTransaction() {

        if(transactionComboBox.getSelectionModel().getSelectedItem() == null){
            errorMessage("No transaction selected");
            return;
        }

        if(transaction.getItemList().isEmpty()){
            Transaction continuedTransaction = (Transaction)transactionComboBox.getSelectionModel().getSelectedItem();
            this.setTransaction(continuedTransaction);
            customerScreenController.setTransaction(continuedTransaction);
            customerScreenController.clearTextFields();
            customerScreenController.updateAmountFields();

            //remove transaction from paused
            for(int i = 0; i < transactionLog.getPausedTransactions().size(); i++){
                if(transactionLog.getPausedTransactions().get(i).getId() == continuedTransaction.getId()){
                    transactionLog.getPausedTransactions().remove(i);
                }
            }

        } else{
            errorMessage("Complete / Pause ongoing transaction before continuing");
            return;

        }
    }

    @FXML
    private void startPayment() {
        customerScreenController.pay();
    }

    @FXML
    private void addDiscount() {    // Handles all discount related stuff
                                    // Discount must be between 1-100
        Double discount;
        Double price;
        Double newPrice;
        System.out.println("Add discount");
        Item item = new Item();
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        try {
            if (Double.parseDouble(discountTextField.getText()) <= 100 && Double.parseDouble(discountTextField.getText()) >= 1) {
                price = selectedItem.getPrice();
                discount = (Double.parseDouble(discountTextField.getText()) / 100);
                newPrice = item.calculateDiscount(price, discount); //  Item/calculateDiscount()
                selectedItem.setPrice(newPrice);
                transaction.setTotalCost((transaction.getTotalCost() - price) + newPrice);
                customerScreenController.updateAmountFields();
                itemListView.refresh();
                customerScreenController.getItemListView().refresh();
            }
        } catch (Exception e) {
            errorMessage("Invalid discount");
        }
        discountTextField.clear();
    }

    public void setTransaction(Transaction transaction){
        this.transaction = transaction;
        itemListView.setItems((ObservableList) transaction.getItemList());
    }

    @FXML
    private void addItem() throws CloneNotSupportedException {
        if (catalogListView.getSelectionModel().getSelectedItem() == null){
            return;
        }
        Item selectedItem = (Item) catalogListView.getSelectionModel().getSelectedItem();
        Item clonedItem = (Item) selectedItem.clone();
        transaction.addItem(clonedItem);
        customerScreenController.updateAmountFields();
    }

    @FXML
    private void removeItem() {
        if(itemListView.getSelectionModel().getSelectedItem() == null){
            return;
        }

        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.removeItem(selectedItem);
        customerScreenController.updateAmountFields();

    }

    public void setCustomerScreenController(CustomerScreenController customerScreenController){
        this.customerScreenController = customerScreenController;
    }

    public TextField getTotalTextField() {
        return totalTextField;
    }

    public void setTotalTextField(TextField totalTextField) {
        this.totalTextField = totalTextField;
    }

    public TextField getBarcodeTextField() {
        return barcodeTextField;
    }

    public void setBarcodeTextField(TextField barcodeTextField) {
        this.barcodeTextField = barcodeTextField;
    }

    public TextField getDiscountTextField() {
        return discountTextField;
    }

    public void setDiscountTextField(TextField discountTextField) {
        this.discountTextField = discountTextField;
    }

    public TextField getChangeTextField() {
        return changeTextField;
    }

    public void setChangeTextField(TextField changeTextField) {
        this.changeTextField = changeTextField;
    }

    public ComboBox getTransactionComboBox() {
        return transactionComboBox;
    }

    public void setTransactionComboBox(ComboBox transactionComboBox) {
        this.transactionComboBox = transactionComboBox;
    }

    public void startPause(){
        pauseTransaction();
    }

    private boolean passwordAlert() {   // A not-so-serious login window to restrict access to admin
                                        // There is no password, just type whatever you fancy or leave it empty
        boolPass = false;

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter password");
        dialog.setHeaderText("Restricted access, enter password to continue");
        dialog.setContentText("Enter password:");

        Optional<String> correct = dialog.showAndWait();

        if (correct.isPresent()) {
            boolPass = true;
        }

        return boolPass;
    }

    private void errorMessage(String msg) {     // Helper method creating an error alert
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public ListView getCatalogListView(){

        ListView catalogListView = this.catalogListView;
        return catalogListView;
    }

}