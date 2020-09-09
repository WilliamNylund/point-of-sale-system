package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.StandardSocketOptions;

public class CustomerScreenController {

    // id: itemListView
    // id: totalTextField
    // id: outstandingTextField
    // id: cardTextField
    // id: cashTextField
    // id: bonusTextField
    // id: recieptCheckBox



    @FXML
    private TextField totalTextField;


    @FXML
    private void pay(){ //id: payButton
        System.out.println("paying");
        totalTextField.setText("YOOO");
    }

    @FXML
    private void addItem(){ //id: addItemButton
        System.out.println("adding item");
    }

    @FXML
    private void removeItem(){ //id: removeItemButton
        System.out.println("removing item");
    }

}
