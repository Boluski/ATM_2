/**
 * Sample Skeleton for 'Client.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientController {

    @FXML
    private ListView<String> accountListView;

//    @FXML // fx:id="accountToggle"
//    private ToggleGroup accountToggle; // Value injected by FXMLLoader

    @FXML // fx:id="amountLabel"
    private Label amountLabel; // Value injected by FXMLLoader

    @FXML // fx:id="balanceButton"
    private Button balanceButton; // Value injected by FXMLLoader

    @FXML // fx:id="balanceLabel"
    private Label balanceLabel; // Value injected by FXMLLoader

    @FXML // fx:id="billButton"
    private Button billButton; // Value injected by FXMLLoader

//    @FXML // fx:id="checkingRadio"
//    private RadioButton checkingRadio; // Value injected by FXMLLoader

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

//    @FXML // fx:id="savingsRadio"
//    private RadioButton savingsRadio; // Value injected by FXMLLoader

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
    float totalAmount = 0;
    float maxAmount = 1000;
    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    public void initialize(){

    }

    public void setData(Client user){
        currentUser = user;
        System.out.println(currentUser);

        emailLabel.setText(currentUser.getEmail());
        phoneLabel.setText(currentUser.getPhoneNumber());
        fullNameLabel.setText(currentUser.getFullName());
        balanceLabel.textProperty().bind(currentUser.observableGrandTotal);
        accountListView.setItems(currentUser.observableCheckingAndSaving);
//        accountListView.getItems().addAll(currentUser.getAllCheckingAndSavingsAccounts());
    }

    @FXML
    void handleBalanceButton(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("Balance.fxml"));
            Scene scene = new Scene(root.load(), 400, 300);
            BalanceController controller = root.getController();
            controller.setDate(currentUser);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Balance");

            dialog.setScene(scene);
            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleBillButton(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("PayBill.fxml"));
            Scene scene = new Scene(root.load(), 400, 175);
            PayBillController controller = root.getController();
            controller.setDate(currentUser);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Pay Bill");

            dialog.setScene(scene);
            dialog.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
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
        try {
            root = new FXMLLoader(ATM.class.getResource("Deposit.fxml"));
            Scene scene = new Scene(root.load(), 400, 150);
            DepositController controller = root.getController();
            controller.setDate(currentUser);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Deposit");

            dialog.setScene(scene);
            dialog.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleLogOutButton(ActionEvent event) {
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
    void handleTenButton(ActionEvent event) {
        float testAmount = totalAmount + (float) 10.00;
        if(testAmount <= maxAmount){
            totalAmount = testAmount;
            amountLabel.setText(String.format("$%.2f", totalAmount));
        }else {
            tenButton.setDisable(true);
        }

    }

    @FXML
    void handleThertyButton(ActionEvent event) {
        float testAmount = totalAmount + (float) 30.00;
        if(testAmount <= maxAmount){
            totalAmount = testAmount;
            amountLabel.setText(String.format("$%.2f", totalAmount));
        }else {
            thertyButton.setDisable(true);
        }
    }

    @FXML
    void handleTransferButton(ActionEvent event) {
        try {
            root = new FXMLLoader(ATM.class.getResource("Transfer.fxml"));
            Scene scene = new Scene(root.load(), 400, 175);
            TransferController controller = root.getController();
            controller.setDate(currentUser);

            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(((Node)(event.getSource())).getScene().getWindow());
            dialog.setTitle("Transfer");

            dialog.setScene(scene);
            dialog.showAndWait();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void handleTwentyButton(ActionEvent event) {
        float testAmount = totalAmount + (float) 20.00;
        if(testAmount <= maxAmount){
            totalAmount = testAmount;
            amountLabel.setText(String.format("$%.2f", totalAmount));
        }else {
            twentyButton.setDisable(true);
        }
    }

    @FXML
    void handleWithdrawButton(ActionEvent event) {
        ObservableList<String> selectedAccount =  accountListView.getSelectionModel().getSelectedItems();

        if (selectedAccount.isEmpty()){
            errorAlert.setHeaderText("No Account!");
            errorAlert.setContentText("In other to make a withdrawal, you must select one of you accounts!");
            errorAlert.showAndWait();

        }else {
            String account = selectedAccount.getFirst();

            System.out.println(totalAmount);
            System.out.println(account);

            if (totalAmount != 0){
                if (currentUser.asPaperMoney(totalAmount)){
                    if (currentUser.withdraw(account, totalAmount)){
                        infoAlert.setHeaderText("Congrats!");
                        infoAlert.setContentText("Money as been withdrawn!");
                        infoAlert.showAndWait();

                        totalAmount = 0;
                        amountLabel.setText("$0.00");
                        tenButton.setDisable(false);
                        twentyButton.setDisable(false);
                        thertyButton.setDisable(false);
                    }else {
                        if (currentUser.asLineOfCreditAccount()){
                            infoAlert.setHeaderText("Line of credit charged");
                            infoAlert.setContentText("Because you don't have enough money in this account, " +
                                    "Your line of credit will be charged for the rest of the payment!");
                            infoAlert.showAndWait();

                            currentUser.withdrawAndUseLineOfCredit(account, totalAmount);

                            infoAlert.setHeaderText("Success!");
                            infoAlert.setContentText("Money as been withdrawn");
                            infoAlert.showAndWait();

                            totalAmount = 0;
                            amountLabel.setText("$0.00");
                            tenButton.setDisable(false);
                            twentyButton.setDisable(false);
                            thertyButton.setDisable(false);


                        }else {
                            errorAlert.setHeaderText("Insufficient Amount!");
                            errorAlert.setContentText("You Don't have enough money in this account!");
                            errorAlert.showAndWait();

                            totalAmount = 0;
                            amountLabel.setText("$0.00");
                            tenButton.setDisable(false);
                            twentyButton.setDisable(false);
                            thertyButton.setDisable(false);
                        }
                    }

                }else {
                    errorAlert.setHeaderText("No paper money!");
                    errorAlert.setContentText("Not enough paper money to carry out this withdrawal!");
                    errorAlert.showAndWait();

                    totalAmount = 0;
                    amountLabel.setText("$0.00");
                    tenButton.setDisable(false);
                    twentyButton.setDisable(false);
                    thertyButton.setDisable(false);
                }

            }else {
                errorAlert.setHeaderText("No amount!");
                errorAlert.setContentText("Please select how much to withdraw!");
                errorAlert.showAndWait();
            }



        }


    }

}
