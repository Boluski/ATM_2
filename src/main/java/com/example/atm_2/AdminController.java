/**
 * Sample Skeleton for 'Admin.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AdminController {

    @FXML // fx:id="accountsComboBox"
    private ComboBox<?> accountsComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="addAccountButton"
    private Button addAccountButton; // Value injected by FXMLLoader

    @FXML // fx:id="allLOCButton"
    private Button allLOCButton; // Value injected by FXMLLoader

    @FXML // fx:id="allSavingsButton"
    private Button allSavingsButton; // Value injected by FXMLLoader

    @FXML // fx:id="blockClientCheckBox"
    private CheckBox blockClientCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="clientsComboBox"
    private ComboBox<?> clientsComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="createClientButton"
    private Button createClientButton; // Value injected by FXMLLoader

    @FXML // fx:id="logOutButton"
    private Button logOutButton; // Value injected by FXMLLoader

    @FXML // fx:id="paperMoneyTextField"
    private TextField paperMoneyTextField; // Value injected by FXMLLoader

    @FXML // fx:id="saveButton"
    private Button saveButton; // Value injected by FXMLLoader

    @FXML // fx:id="showTransactionButton"
    private Button showTransactionButton; // Value injected by FXMLLoader

    @FXML // fx:id="withdrawButton"
    private Button withdrawButton; // Value injected by FXMLLoader

    @FXML
    void handleAccountSelected(ActionEvent event) {

    }

    @FXML
    void handleAddAccount(ActionEvent event) {

    }

    @FXML
    void handleAllSavings(ActionEvent event) {

    }

    @FXML
    void handleClientChange(ActionEvent event) {

    }

    @FXML
    void handleClinetBlock(ActionEvent event) {

    }

    @FXML
    void handleCreateClient(ActionEvent event) {

    }

    @FXML
    void handleLOC(ActionEvent event) {

    }

    @FXML
    void handleLogOut(ActionEvent event) {

    }

    @FXML
    void handleSave(ActionEvent event) {

    }

    @FXML
    void handleShowTransaction(ActionEvent event) {

    }

    @FXML
    void handleWithdraw(ActionEvent event) {

    }

}
