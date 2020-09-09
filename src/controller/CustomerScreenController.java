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

    private ObservableList<String> items = FXCollections.observableArrayList();



    @FXML
    private void pay(){ //id: payButton
        System.out.println("paying");



        if(receiptCheckBox.isSelected()){ //if receipt

        }
    }

    @FXML
    private void addItem(){ //id: addItemButton
        System.out.println("adding item");


        for(int i = 0; i < 6; i++){
            Item item = new Item("banana", i, 69.69);
            items.add(item.toString());

        }
        itemListView.setItems(items);

    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        System.out.println("removing item");
        ObservableList selectedIndices = itemListView.getSelectionModel().getSelectedIndices();
        System.out.println(items);
        for(int i = 0; i< selectedIndices.size(); i++){
            System.out.println(selectedIndices.get(i).getClass());
        }


        itemListView.refresh();

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
