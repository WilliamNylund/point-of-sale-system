package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Item;
import model.ProductCatalog;
import model.TransactionLog;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.SQLException;
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

    private CustomerScreenController customerScreenController;
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
       Map<String, Integer> productsSold = transactionLog.getProductsSoldByDate(startDate, endDate, searchWord,"FEMALE");

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
    @FXML
    private void setSelectedItemPrice(){
        Item item=getSelectedItem();
        Double newprice=getNewPrice();
        item.setPrice(newprice);
        uppdateViewAfterPriceUppdate();
        // TODO se till att get item by name, id ska ha nya värdet

    }
    @FXML
    private void uppdatePricesToProductCatalog() throws ParserConfigurationException, SAXException, IOException, SQLException {

        List<Item> allitems= allProductsListView.getItems();
        productCatalog.uppdatePrices(allitems);

    }

    private void uppdateViewAfterPriceUppdate() {
        allProductsListView.refresh();
        selectedItemTextFieldPrice.clear();
        setPriceTextField.clear();
        customerScreenController.getCatalogListView().refresh();
        customerScreenController.cashierScreenController.getCatalogListView().refresh();





    }

    private Item getSelectedItem() {

        return (Item) allProductsListView.getSelectionModel().getSelectedItem();
    }
    private double getNewPrice() {
        return Double.parseDouble(setPriceTextField.getText());
    }


    public void setcustomerScreenController(CustomerScreenController customerScreenController) {
        this.customerScreenController = customerScreenController;
    }
}

