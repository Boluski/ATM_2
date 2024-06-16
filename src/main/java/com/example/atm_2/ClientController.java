/**
 * Sample Skeleton for 'Client.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Dialog;

import java.io.IOException;

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
    FXMLLoader root;

    public void initialize(){

    }

    public void setData(Client user){
        currentUser = user;
        System.out.println(currentUser);

        emailLabel.setText(currentUser.getEmail());
        phoneLabel.setText(currentUser.getPhoneNumber());
        fullNameLabel.setText(currentUser.getFullName());
    }

    @FXML
    void handleBalanceButton(ActionEvent event) {

    }

    @FXML
    void handleBillButton(ActionEvent event) {

    }

    @FXML
    void handleCreateButton(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("CreateAccount.fxml"));
            Scene scene = new Scene(root.load(), 400, 150);
            CreateAccountController controller = root.getController();
            controller.setDate(currentUser);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Create Account");

            dialog.setScene(scene);
            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }

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
