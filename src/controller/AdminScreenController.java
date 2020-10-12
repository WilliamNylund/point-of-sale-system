package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.ProductCatalog;
import model.Transaction;
import model.TransactionLog;

import java.security.KeyStore;
import java.time.LocalDate;
import java.util.*;


public class AdminScreenController {

    private ProductCatalog productCatalog = ProductCatalog.getInstance();
    private TransactionLog transactionLog = TransactionLog.getInstance();

    @FXML
    private ListView allProductsListView;

    @FXML
    private TextField setPriceTextField;

    @FXML
    private TextArea productsTextArea;

    @FXML
    private TextField selectedItemTextFieldPrice;

    @FXML
    private TextField selectedItemTextFieldDates;

    @FXML
    private Button setPriceButton;

    @FXML
    private Button statsButton;

    @FXML
    private DatePicker startDateDatePicker;

    @FXML
    private DatePicker endDateDatePicker;

    @FXML
    public void initialize() {
        allProductsListView.setItems(productCatalog.getCatalog());

    }

    public void setSelectedItem() {
        selectedItemTextFieldPrice.setText(allProductsListView.getSelectionModel().getSelectedItem().toString());
    }

    @FXML
    private void showStats() {
        System.out.println("showing stats");
        productsTextArea.clear();
        productsTextArea.appendText("Product --- Amount sold \n\n");

        LocalDate startDate = startDateDatePicker.getValue();
        LocalDate endDate = endDateDatePicker.getValue();
        String searchWord = selectedItemTextFieldDates.getText();
            //get all transactionItems sold inbetween startDate and endDate
        Map<String, Integer> productsSold = transactionLog.getProductsSoldByDate(startDate, endDate, searchWord);

        ArrayList<String> productNames = new ArrayList<>();
        ArrayList<Integer> amountsSold = new ArrayList<>();

        productsSold.entrySet().forEach(entry->{
            productNames.add(entry.getKey());
            amountsSold.add(entry.getValue());
        });
        for(int i=productNames.size()-1; i >= 0; i--){
            productsTextArea.appendText(productNames.get(i) + "  ---  " + amountsSold.get(i) + "\n");
        }
    }
}
