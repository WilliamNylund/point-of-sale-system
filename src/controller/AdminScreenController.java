package controller;

import javafx.collections.FXCollections;
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

    @FXML
    private ComboBox sexComboBox;

    @FXML
    private Spinner startAgeSpinner;

    @FXML
    private Spinner endAgeSpinner;

    @FXML
    private TextField bonusCustomerTextField;

    private CustomerScreenController customerScreenController;
    @FXML
    public void initialize() {
        allProductsListView.setItems(productCatalog.getCatalog());

        ObservableList<String> gender =
                FXCollections.observableArrayList(
                        "",
                        "MALE",
                        "FEMALE",
                        "UNSPECIFIED"
                );

        sexComboBox.setItems(gender);
        startAgeSpinner.getEditor().clear();
        endAgeSpinner.getEditor().clear();

    }

    public void setSelectedItem() {
        selectedItemTextFieldPrice.setText(allProductsListView.getSelectionModel().getSelectedItem().toString());
    }



    @FXML
    private void showStats() {
        productsTextArea.clear();
        productsTextArea.appendText("Product --- Amount sold \n\n");

        LocalDate startDate = startDateDatePicker.getValue();
        LocalDate endDate = endDateDatePicker.getValue();
        String searchWord = selectedItemTextFieldDates.getText();
        String sex = (String)sexComboBox.getSelectionModel().getSelectedItem();

        int startAge = -1;
        int endAge = -1;

        try {
            //if atleast one is not empty
            String startAgeSpinnerValue = startAgeSpinner.getEditor().getText().trim();
            String endAgeSpinnerValue = endAgeSpinner.getEditor().getText().trim();

            if(!startAgeSpinnerValue.isEmpty() || !endAgeSpinnerValue.isEmpty()){
                if((startAgeSpinnerValue.isEmpty()) && (!endAgeSpinnerValue.isEmpty())){
                    System.out.println("1");
                    startAge = 0;
                    endAge = Integer.parseInt(endAgeSpinnerValue);
                    //if endAge is empty but not startAge
                } else if((!startAgeSpinnerValue.isEmpty()) && (endAgeSpinnerValue.isEmpty())) {
                    System.out.println("2");
                    startAge = Integer.parseInt(startAgeSpinnerValue);
                    endAge = 200;
                } else if(!(startAgeSpinnerValue.isEmpty()) && (!endAgeSpinnerValue.isEmpty())){
                    System.out.println("3");
                    startAge = Integer.parseInt(startAgeSpinnerValue);
                    endAge = Integer.parseInt(endAgeSpinnerValue);
                }
            }

        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Only input integers as age");
            alert.showAndWait();
            return;
        }
        //get all transactionItems sold inbetween startDate and endDate
        Map<String, Integer> productsSold = transactionLog.getProductsSoldByDate(startDate, endDate, searchWord, sex, startAge, endAge);


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
    private void showCustomerStats(){
        productsTextArea.clear();
        productsTextArea.appendText("Product --- Amount sold \n\n");
        int customerNo;
        try{
            customerNo = Integer.parseInt(bonusCustomerTextField.getText());
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Input only integers");
            alert.showAndWait();
            return;
        }
        Map<String, Integer> productsSold = transactionLog.getProductsSoldByCustomer(customerNo);


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

