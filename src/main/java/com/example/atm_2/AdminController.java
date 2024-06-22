/**
 * Sample Skeleton for 'Admin.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;

public class AdminController {

    @FXML // fx:id="accountsComboBox"
    private ComboBox<String> accountsComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="addAccountButton"
    private Button addAccountButton; // Value injected by FXMLLoader

    @FXML // fx:id="allLOCButton"
    private Button allLOCButton; // Value injected by FXMLLoader

    @FXML // fx:id="allSavingsButton"
    private Button allSavingsButton; // Value injected by FXMLLoader

    @FXML // fx:id="blockClientCheckBox"
    private CheckBox blockClientCheckBox; // Value injected by FXMLLoader

    @FXML // fx:id="clientsComboBox"
    private ComboBox<String> clientsComboBox; // Value injected by FXMLLoader

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

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private Admin currentAdmin;
    private Client activeClient;
    FXMLLoader root;
    SimpleBooleanProperty checkBox;

    void setDate(Admin admin){
        currentAdmin = admin;
        activeClient = currentAdmin.getClient();
        System.out.println(currentAdmin);

        clientsComboBox.setItems(currentAdmin.Clients);
        clientsComboBox.setValue(currentAdmin.Clients.getFirst());

        accountsComboBox.setItems(activeClient.observableAllAccount);
        accountsComboBox.setValue(activeClient.observableAllAccount.getFirst());

        checkBox = new SimpleBooleanProperty(activeClient.isBlocked());
        blockClientCheckBox.selectedProperty().bindBidirectional(checkBox);

        paperMoneyTextField.setText(String.valueOf(currentAdmin.getPaperMoney()));
    }

    @FXML
    void handleAccountSelected(ActionEvent event) {

    }

    @FXML
    void handleAddAccount(ActionEvent event) {

    }

    @FXML
    void handleAllSavings(ActionEvent event) {
        currentAdmin.addBonusToAllSavings();
    }

    @FXML
    void handleClientChange(ActionEvent event) {
        activeClient = currentAdmin.getClient(clientsComboBox.getSelectionModel().getSelectedItem());

        accountsComboBox.setItems(activeClient.observableAllAccount);
        accountsComboBox.setValue(activeClient.observableAllAccount.getFirst());
        checkBox.set(activeClient.isBlocked());
    }

    @FXML
    void handleClinetBlock(ActionEvent event) {
        System.out.println(checkBox.get());
        activeClient.setAccountAsBlock(checkBox.get());
    }

    @FXML
    void handleCreateClient(ActionEvent event) {

    }

    @FXML
    void handleLOC(ActionEvent event) {

    }

    @FXML
    void handleLogOut(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("Login.fxml"));
            Scene scene = new Scene(root.load());

            Stage stage = new Stage();
            stage.setTitle("ATM");
            stage.setScene(scene);
            stage.show();

            ((Node)(event.getSource())).getScene().getWindow().hide();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleSave(ActionEvent event) {
        float newPaperMoney = Float.parseFloat(paperMoneyTextField.getText());
        if (newPaperMoney > 20000){
            errorAlert.setHeaderText("Max!");
            errorAlert.setContentText("You can't add more than 20,000.00 dollars to the ATM!");
            errorAlert.showAndWait();
        }else {
            currentAdmin.setPaperMoney(newPaperMoney);
        }

    }

    @FXML
    void handleShowTransaction(ActionEvent event) {

    }

    @FXML
    void handleWithdraw(ActionEvent event) {

    }

}
