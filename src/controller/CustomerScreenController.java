package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;
import model.ProductCatalog;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;


public class CustomerScreenController {

    // id: itemListView
    // id: totalTextField
    // id: outstandingTextField
    // id: cardTextField
    // id: cashTextField
    // id: bonusTextField
    // id: receiptCheckBox


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
    private ObservableList<Item> items = FXCollections.observableArrayList();
    /*

    private ObservableList<Item> catalog = FXCollections.observableArrayList();

    @FXML
    private void initialize(){
        for(int i=0;i<10;i++){
            Item catItem = new Item();
            catItem.setName("Leipä nro: "+i);
            catItem.setBarCode(i);
            catItem.setPrice(4.20);
            catalog.add(catItem);
        }


        catalogListView.setItems(catalog);
        itemListView.setItems(items);
    }
    */

    @FXML
    private void pay(){ //id: payButton
        try {
            System.out.println("paying");

            if (receiptCheckBox.isSelected()) { //if receipt

            }
            Runtime.getRuntime().exec("java -jar CashBox.jar");
            URL url = new URL("http://localhost:9001/cashbox/open");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            con.getInputStream();

        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("urmmommagay");
        }
    }

    @FXML
    private void addItem(){ //id: addItemButton
        System.out.println("adding item");
        /*double sum = 0.0;

        for(int i = 0; i < 6; i++){
            Item item = new Item();
            items.add(item);
            sum += item.getPrice();
        }*/
        Item selectedIndex = (Item) catalogListView.getSelectionModel().getSelectedItem();
        items.add(selectedIndex);



        //itemListView.setItems(items);
       // totalTextField.setText(Double.toString(sum));
    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        System.out.println("removing item");
        Item selectedItem = (Item) itemListView.getSelectionModel().getSelectedItem();
        items.remove(selectedItem);
        System.out.println(items);
        //for(int i = 0; i< selectedIndices.size(); i++){
          //  System.out.println(selectedIndices.get(i).getClass());
       // }


        //itemListView.refresh();

    }

    @FXML
    private void holdTransaction(){ //id: holdTransactionButton
        System.out.println("holding transaction");
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
        });
    }

    @FXML
    private void validateCardField() {
        cardTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                cardTextField.setText(oldValue);
            }
        });
    }

}
