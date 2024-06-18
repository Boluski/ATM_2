/**
 * Sample Skeleton for 'Deposit.fxml' Controller Class
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

public class DepositController {

    @FXML // fx:id="accountCombo"
    private ComboBox<String> accountCombo; // Value injected by FXMLLoader

    @FXML // fx:id="amountTextField"
    private TextField amountTextField; // Value injected by FXMLLoader

    @FXML // fx:id="depositButton"
    private Button depositButton; // Value injected by FXMLLoader

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    Client currentUser;

    public static boolean isAlphabet(String input) {
        return input.matches(".*[A-Za-z].*");
    }

    public void setDate(Client user){
        currentUser = user;

        depositButton.setDisable(true);



        accountCombo.getItems().addAll(currentUser.getCanDepositAccounts());
        accountCombo.setValue(currentUser.getCanDepositAccounts().getFirst());
    }

    @FXML
    void handleChange(KeyEvent event) {
        try {
            Character c = amountTextField.getText().charAt(0);
            if (amountTextField.getText().isEmpty() || c.equals('-')){
                depositButton.setDisable(true);
            }else {
                depositButton.setDisable(isAlphabet(amountTextField.getText()));
            }
        }catch (Exception e){

        }
    }

    @FXML
    void handleDeposit(ActionEvent event) {
        String account = accountCombo.getValue();
        float amount = Float.parseFloat(amountTextField.getText());

        currentUser.deposit(account, amount);

        infoAlert.setHeaderText("Deposited!");
        infoAlert.setContentText("Your money has been deposited successfully!");
        infoAlert.showAndWait();

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
