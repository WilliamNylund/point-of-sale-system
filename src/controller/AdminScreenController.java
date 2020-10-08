package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.ProductCatalog;


public class AdminScreenController {

    private ProductCatalog productCatalog = ProductCatalog.getInstance();

    @FXML
    private ListView allProductsListView;

    @FXML
    private TextField setPriceTextField;

    @FXML
    private TextField selectedItemTextFieldPrice;

    @FXML
    private TextField selectedItemTextFieldDates;

    @FXML
    private Button setPriceButton;

    @FXML
    private Button statsButton;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    public void initialize() {
        allProductsListView.setItems(productCatalog.getCatalog());

    }

    public void setSelectedItem() {
        selectedItemTextFieldPrice.setText(allProductsListView.getSelectionModel().getSelectedItem().toString());
    }
}
