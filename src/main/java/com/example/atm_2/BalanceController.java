/**
 * Sample Skeleton for 'Balance.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class BalanceController {

    @FXML // fx:id="accountNameListView"
    private ListView<String> accountNameListView; // Value injected by FXMLLoader

    @FXML // fx:id="balanceListView"
    private ListView<String> balanceListView; // Value injected by FXMLLoader

    @FXML // fx:id="closeButton"
    private Button closeButton; // Value injected by FXMLLoader

    @FXML // fx:id="totalLable"
    private Label totalLable; // Value injected by FXMLLoader

    Client currentUser;
    public void setDate(Client user){
        currentUser = user;

        accountNameListView.getItems().addAll(currentUser.getAllAccounts());
        balanceListView.getItems().addAll(currentUser.getAllBalance());
        totalLable.setText(currentUser.getGrandTotal());

//        accountCombo.getItems().addAll();
//        accountCombo.setValue(currentUser.getAllCheckingAccounts().getFirst());
    }

    @FXML
    void handleClose(ActionEvent event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
