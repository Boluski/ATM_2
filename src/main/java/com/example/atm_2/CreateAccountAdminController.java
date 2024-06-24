/**
 * Sample Skeleton for 'CreateAccountAdmin.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class CreateAccountAdminController {

    @FXML // fx:id="accountNameTextField"
    private TextField accountNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="accountTypeCombo"
    private ComboBox<String> accountTypeCombo; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    Client currentUser;
    private ComboBox<String> accountsComboBox;
    void setData(Client user, ComboBox<String> accountsComboBox){
        currentUser = user;
        this.accountsComboBox = accountsComboBox;

        createButton.setDisable(true);

        ArrayList<String> accounts = new ArrayList<>();

        accounts.add("Checking");
        accounts.add("Savings");
        accounts.add("Mortgage");
        accounts.add("Line Of Credit");

        if(!currentUser.asCheckingAccount()){
            accounts.remove(1);
            accounts.remove(1);
            accounts.remove(1);
        }else{
            if (currentUser.asLineOfCreditAccount()){
                accounts.remove(3);
            }
        }

        accountTypeCombo.getItems().addAll(accounts);
        accountTypeCombo.setValue("Checking");
    }

    @FXML
    void handleChange(KeyEvent event) {
        String text = accountNameTextField.getText();

        if (text.length() == 0){
            createButton.setDisable(true);
        }else {
            createButton.setDisable(false);
        }
    }

    @FXML
    void handleCreate(ActionEvent event) {
        String selected = accountTypeCombo.getValue();
        String accountName = accountNameTextField.getText();
        System.out.println(selected);


        if(selected.equals("Checking")){
            currentUser.addCheckingAccount(accountName);
        }else if(selected.equals("Savings")){
            currentUser.addSavingAccount(accountName);
        }else if (selected.equals("Mortgage")){
            currentUser.addMortgageAccount(accountName);
        } else if (selected.equals("Line Of Credit")) {
            currentUser.addLineOfCreditAccount(accountName);
        }

        accountsComboBox.setDisable(false);
        accountsComboBox.setValue(currentUser.observableAllAccount.getFirst());

        infoAlert.setHeaderText("Success!");
        infoAlert.setContentText("Your Account has been Created!");
        infoAlert.showAndWait();

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
