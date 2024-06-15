/**
 * Sample Skeleton for 'Client.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class ClientController {

    @FXML // fx:id="accountToggle"
    private ToggleGroup accountToggle; // Value injected by FXMLLoader

    @FXML // fx:id="amountLabel"
    private Label amountLabel; // Value injected by FXMLLoader

    @FXML // fx:id="balanceButton"
    private Button balanceButton; // Value injected by FXMLLoader

    @FXML // fx:id="balanceLabel"
    private Label balanceLabel; // Value injected by FXMLLoader

    @FXML // fx:id="billButton"
    private Button billButton; // Value injected by FXMLLoader

    @FXML // fx:id="checkingRadio"
    private RadioButton checkingRadio; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="depositButton"
    private Button depositButton; // Value injected by FXMLLoader

    @FXML // fx:id="emailLabel"
    private Label emailLabel; // Value injected by FXMLLoader

    @FXML // fx:id="fullNameLabel"
    private Label fullNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="logOutButton"
    private Button logOutButton; // Value injected by FXMLLoader

    @FXML // fx:id="phoneLabel"
    private Label phoneLabel; // Value injected by FXMLLoader

    @FXML // fx:id="savingsRadio"
    private RadioButton savingsRadio; // Value injected by FXMLLoader

    @FXML // fx:id="tenButton"
    private Button tenButton; // Value injected by FXMLLoader

    @FXML // fx:id="thertyButton"
    private Button thertyButton; // Value injected by FXMLLoader

    @FXML // fx:id="transferButton"
    private Button transferButton; // Value injected by FXMLLoader

    @FXML // fx:id="twentyButton"
    private Button twentyButton; // Value injected by FXMLLoader

    @FXML // fx:id="withdrawButton"
    private Button withdrawButton; // Value injected by FXMLLoader

    Client currentUser;

    public void initialize(){

    }

    public void setData(Client user){
        currentUser = user;
        System.out.println("In Client");
        System.out.println(currentUser);
    }

    @FXML
    void handleBalanceButton(ActionEvent event) {

    }

    @FXML
    void handleBillButton(ActionEvent event) {

    }

    @FXML
    void handleCreateButton(ActionEvent event) {

    }

    @FXML
    void handleDepositButton(ActionEvent event) {

    }

    @FXML
    void handleLogOutButton(ActionEvent event) {

    }

    @FXML
    void handleTenButton(ActionEvent event) {

    }

    @FXML
    void handleThertyButton(ActionEvent event) {

    }

    @FXML
    void handleTransferButton(ActionEvent event) {

    }

    @FXML
    void handleTwentyButton(ActionEvent event) {

    }

    @FXML
    void handleWithdrawButton(ActionEvent event) {

    }

}
