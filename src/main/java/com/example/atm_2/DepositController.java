/**
 * Sample Skeleton for 'Deposit.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class DepositController {

    @FXML // fx:id="accountCombo"
    private ComboBox<?> accountCombo; // Value injected by FXMLLoader

    @FXML // fx:id="amountTextField"
    private TextField amountTextField; // Value injected by FXMLLoader

    @FXML // fx:id="depositButton"
    private Button depositButton; // Value injected by FXMLLoader

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    Client currentUser;

    public void setDate(Client user){
        currentUser = user;

        depositButton.setDisable(true);

//        ArrayList<String> accounts = new ArrayList<>();
//
//        accounts.add("Checking");
//        accounts.add("Savings");
//        accounts.add("Mortgage");
//        accounts.add("Line Of Credit");
//
//        if(!currentUser.asCheckingAccount()){
//            accounts.remove(1);
//            accounts.remove(1);
//            accounts.remove(1);
//        }else{
//            if (currentUser.asLineOfCreditAccount()){
//                accounts.remove(3);
//            }
//        }
//
//        accountTypeCombo.getItems().addAll(accounts);
//        accountTypeCombo.setValue("Checking");
    }

    @FXML
    void handleChange(KeyEvent event) {

    }

    @FXML
    void handleDeposit(ActionEvent event) {

    }

}
