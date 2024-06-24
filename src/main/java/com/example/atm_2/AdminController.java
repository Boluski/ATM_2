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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.util.ArrayList;

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

        withdrawButton.setDisable(!activeClient.isMortgageAccount(activeClient.observableAllAccount.getFirst()));

        checkBox = new SimpleBooleanProperty(activeClient.isBlocked());
        blockClientCheckBox.selectedProperty().bindBidirectional(checkBox);

        paperMoneyTextField.setText(String.valueOf(currentAdmin.getPaperMoney()));
    }

    @FXML
    void handleAccountSelected(ActionEvent event) {
        String selectedAccount = accountsComboBox.getSelectionModel().getSelectedItem();
        withdrawButton.setDisable(!activeClient.isMortgageAccount(selectedAccount));
    }

    @FXML
    void handleAddAccount(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("CreateAccountAdmin.fxml"));
            Scene scene = new Scene(root.load(), 400, 150);
            CreateAccountAdminController controller = root.getController();
            controller.setData(activeClient, accountsComboBox);

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
    void handleAllSavings(ActionEvent event) {
        currentAdmin.addBonusToAllSavings();
    }

    @FXML
    void handleClientChange(ActionEvent event) {
        accountsComboBox.setDisable(false);
        activeClient = currentAdmin.getClient(clientsComboBox.getSelectionModel().getSelectedItem());

        accountsComboBox.setItems(activeClient.observableAllAccount);
        try {
            accountsComboBox.setValue(activeClient.observableAllAccount.getFirst());
        }catch (Exception e){
            accountsComboBox.setDisable(true);
        }

        checkBox.set(activeClient.isBlocked());
    }

    @FXML
    void handleClinetBlock(ActionEvent event) {
        System.out.println(checkBox.get());
        activeClient.setAccountAsBlock(checkBox.get());
    }

    @FXML
    void handleCreateClient(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("CreateClient.fxml"));
            Scene scene = new Scene(root.load(), 400, 300);
            CreateClientController controller = root.getController();
            controller.setData(currentAdmin);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Create Client");

            dialog.setScene(scene);
            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void handleLOC(ActionEvent event) {
        currentAdmin.addInterest(currentAdmin.getCode());
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
        ArrayList<Transaction> transactions;
        transactions = activeClient.getTransactions(accountsComboBox.getSelectionModel().getSelectedItem());
        try {
            root = new FXMLLoader(ATM.class.getResource("Transaction.fxml"));
            Scene scene = new Scene(root.load());
            TransactionController controller = root.getController();
            controller.setData(transactions);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Transaction");

            dialog.setScene(scene);
            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleWithdraw(ActionEvent event) {
        String mortgageAccount = accountsComboBox.getSelectionModel().getSelectedItem();
        try {
            root = new FXMLLoader(ATM.class.getResource("Withdraw.fxml"));
            Scene scene = new Scene(root.load(), 400, 150);
            WithdrawController controller = root.getController();
            controller.setDate(activeClient, mortgageAccount, currentAdmin.getCode());

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Withdraw");

            dialog.setScene(scene);
            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
