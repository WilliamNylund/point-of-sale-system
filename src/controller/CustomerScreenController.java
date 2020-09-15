package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Item;

import java.net.StandardSocketOptions;

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
    private TextField outstandingTextField;
    @FXML
    private TextField cardTextField;
    @FXML
    private TextField cashTextField;
    @FXML
    private TextField bonusTextField;
    @FXML
    private CheckBox receiptCheckBox;


    @FXML
    private void pay(){ //id: payButton
        System.out.println("paying");
    }

    @FXML
    private void addItem(){ //id: addItemButton
        System.out.println("adding item");
        Item item = new Item("banana", 420.69);
        ObservableList<String> names = FXCollections.observableArrayList(
                "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
        itemListView.setItems(names);
        System.out.println(item.getName());
    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        System.out.println("removing item");
    }
    @FXML
    private void holdTransaction(){ //id: holdTransactionButton
        System.out.println("holding transaction");
    }
    @FXML
    private void continueTransaction(){ //id: continueTransactionButton
        System.out.println("continue transaction");
    }

}
