package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import model.ProductCatalog;
import model.Transaction;

import java.io.IOException;
import java.util.List;

public class CashierScreenController {

    private ProductCatalog productCatalog = ProductCatalog.getInstance();

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

    Transaction transaction;


    @FXML
    private void initialize() {
        catalogListView.setItems(productCatalog.getCatalog());
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
            if (item != null) {
                transaction.addItem(item);
                customerScreenController.updateAmountFields();
            }
        } catch (Exception e) {

        }
        try {
            Item item = productCatalog.getProductByName(barcodeTextField.getText());
            if (item != null) {
                transaction.addItem(item);
                customerScreenController.updateAmountFields();
            }
        } catch (Exception e) {

        }
        try {
            List item = productCatalog.getProductByKeyWord((barcodeTextField.getText()));
            System.out.println(item);
        } catch (Exception e) {
            System.out.println("sum ting wong");
        }
    }

    @FXML
    private void pauseSale() throws IOException {
        System.out.println("Pause Sale");
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
        // TODO update price view in real time, right now only updates when adding/removing items
        System.out.println("Add discount");
        Double discount = 0.0;
        Double price = 0.0;
        Double newPrice = 0.0;
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        if (Double.parseDouble(discountTextField.getText()) <= 100) {
            if (discountTextField.getText().contains(".")) {
                price = selectedItem.getPrice();
                discount = Double.parseDouble(discountTextField.getText());
                newPrice = transaction.calculateDiscount(price, discount);
                selectedItem.setPrice(newPrice);
            } else {
                price = selectedItem.getPrice();
                discount = (Double.parseDouble(discountTextField.getText()) / 100);
                newPrice = transaction.calculateDiscount(price, discount);
                selectedItem.setPrice(newPrice);
            }
        } else {
            discountTextField.setText("Invalid discount");
        }
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

        //totalTextField.setText(Double.toString(transaction.getTotalCost()));
        customerScreenController.updateAmountFields();
    }

    @FXML
    private void removeItem() {
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        transaction.removeItem(selectedItem);

        //totalTextField.setText(Double.toString(transaction.getTotalCost()));
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
}
