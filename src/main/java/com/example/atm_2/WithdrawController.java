/**
 * Sample Skeleton for 'Withdraw.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class WithdrawController {

    @FXML // fx:id="amountTextField"
    private TextField amountTextField; // Value injected by FXMLLoader

    @FXML // fx:id="withdrawButton"
    private Button withdrawButton; // Value injected by FXMLLoader

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public static boolean isAlphabet(String input) {
        return input.matches(".*[A-Za-z].*");
    }
    Client currentUser;
    String mortgageAccount;
    String admin;

    public void setDate(Client user, String accountName, String admin){
        currentUser = user;
        this.mortgageAccount = accountName;
        this.admin = admin;

    }

    @FXML
    void handleChange(KeyEvent event) {
        try {
            Character c = amountTextField.getText().charAt(0);
            if (amountTextField.getText().isEmpty() || c.equals('-')){
                withdrawButton.setDisable(true);
            }else {
                withdrawButton.setDisable(isAlphabet(amountTextField.getText()));
            }
        }catch (Exception e){

        }
    }

    @FXML
    void handleWithdraw(ActionEvent event) {
        float amount = Float.parseFloat(amountTextField.getText());

        if (amount != 0){

            if (currentUser.withdrawMortgage(mortgageAccount, amount, admin)){
                infoAlert.setHeaderText("Congrats!");
                infoAlert.setContentText("Money as been withdrawn!");
                infoAlert.showAndWait();

                }else {
                    if (currentUser.asLineOfCreditAccount()){
                        infoAlert.setHeaderText("Line of credit charged");
                        infoAlert.setContentText("Because you don't have enough money in this account, " +
                                "Your line of credit will be charged for the rest of the payment!");
                        infoAlert.showAndWait();

                        currentUser.withdrawMortgageAndUseLineOfCredit(mortgageAccount, amount, admin);

                        infoAlert.setHeaderText("Success!");
                        infoAlert.setContentText("Money as been withdrawn");
                        infoAlert.showAndWait();


                    }else {
                        errorAlert.setHeaderText("Insufficient Amount!");
                        errorAlert.setContentText("You Don't have enough money in this account!");
                        errorAlert.showAndWait();


                    }
                }

        }else {
            errorAlert.setHeaderText("No amount!");
            errorAlert.setContentText("Please select how much to withdraw!");
            errorAlert.showAndWait();
        }

        ((Node)(event.getSource())).getScene().getWindow().hide();
    }

}
