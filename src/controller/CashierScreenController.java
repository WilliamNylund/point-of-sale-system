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
    public TextField totalTextField;

    @FXML
    public TextField changeTextField;

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
    private void openAdminWindow() {
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
    private void searchItem() {
        System.out.println(barcodeTextField.getText());
        if (barcodeTextField.getText().isEmpty()) {
            System.out.println("cant search with empty");
        } else {
            try {
                Item item = productCatalog.getProductByName(barcodeTextField.getText());
                if (item != null) {
                    transaction.addItem(item);
                    customerScreenController.updateAmountFields();
                }
            } catch (Exception e) {

            }

            try {
                Item item = productCatalog.getProductByBarCode(Integer.parseInt(barcodeTextField.getText()));
                if (item != null) {
                    transaction.addItem(item);
                    customerScreenController.updateAmountFields();
                }
            } catch (Exception e) {

            }

        /*try {
            List item = productCatalog.getProductByKeyWord((barcodeTextField.getText()));
            System.out.println(item);
        } catch (Exception e) {
            System.out.println("sum ting wong");
        }*/
        }
    }

    @FXML
    private void pauseTransaction() {
        System.out.println("Pause transaction");

        if(transaction.getItemList().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Transaction is empty! Nothing to pause dumbass..");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Select a transaction");
            alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText(null);
            alert.setContentText("Complete / pause ongoing transactionen before continuing");
            alert.showAndWait();
            return;

        }
    }

    @FXML
    private void startPayment() {
        customerScreenController.pay();
        Double change = ((transaction.getCashAmount() + transaction.getCardAmount()) - transaction.getTotalCost());
        if (change > 0.0)
            changeTextField.setText(String.valueOf(change));
        else
            changeTextField.setText("No change");
    }

    @FXML
    private void addDiscount() {

        System.out.println("Add discount");
        Item item = new Item();
        Double discount;
        Double price;
        Double newPrice;
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        if (Double.parseDouble(discountTextField.getText()) <= 100) {
            price = selectedItem.getPrice();
            discount = (Double.parseDouble(discountTextField.getText()) / 100);
            newPrice = item.calculateDiscount(price, discount);
            selectedItem.setPrice(newPrice);
            customerScreenController.updateAmountFields();
            itemListView.refresh();
            customerScreenController.getItemListView().refresh();
        } else {
            discountTextField.setText("Invalid discount");
        }
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

    public ComboBox getTransactionComboBox() {
        return transactionComboBox;
    }

    public void setTransactionComboBox(ComboBox transactionComboBox) {
        this.transactionComboBox = transactionComboBox;
    }

    public void startPause(){
        pauseTransaction();
    }

    private boolean passwordAlert() {
        boolPass = false;

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Enter password");
        dialog.setHeaderText("Restricted access, enter password to continue");
        dialog.setContentText("Enter password:");
        //PasswordField password = new PasswordField();
        //password.setPromptText("Password");

        Optional<String> correct = dialog.showAndWait();

        if (correct.isPresent()) {
            boolPass = true;
        }

        return boolPass;
    }
    public ListView getCatalogListView(){

        ListView catalogListView = this.catalogListView;
        return catalogListView;
    }

    @FXML
    private void validateDiscountField() {
        discountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                discountTextField.setText(oldValue);
            }
        });
    }
}