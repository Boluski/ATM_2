/**
 * Sample Skeleton for 'Paybill.fxml' Controller Class
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

public class PayBillController {

    @FXML // fx:id="accountCombo"
    private ComboBox<String> accountCombo; // Value injected by FXMLLoader

    @FXML // fx:id="amountTextField"
    private TextField amountTextField; // Value injected by FXMLLoader

    @FXML // fx:id="billNameTextField"
    private TextField billNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button payButton; // Value injected by FXMLLoader

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Client currentUser;

    public static boolean isAlphabet(String input) {
        return input.matches(".*[A-Za-z].*");
    }

    public void setDate(Client user){
        currentUser = user;

        payButton.setDisable(true);

        accountCombo.getItems().addAll(currentUser.getAllCheckingAccounts());
        accountCombo.setValue(currentUser.getAllCheckingAccounts().getFirst());
    }

    @FXML
    void handleChange(KeyEvent event) {
        try {
            Character c = amountTextField.getText().charAt(0);
            if (amountTextField.getText().isEmpty() || c.equals('-')){
                payButton.setDisable(true);
            }else {
                payButton.setDisable(isAlphabet(amountTextField.getText()));
            }
        }catch (Exception e){

        }
    }

    @FXML
    void handlePay(ActionEvent event) {
        String accountName = accountCombo.getValue();
        String billName = billNameTextField.getText();
        float amount = Float.parseFloat(amountTextField.getText());

        if (billName.isEmpty()){
            errorAlert.setHeaderText("No bill name!");
            errorAlert.setContentText("Make sure to give this bill payment a name.");
            errorAlert.showAndWait();
        }else {
            if (currentUser.payBill(accountName, billName, amount)){
                infoAlert.setHeaderText("Payed!");
                infoAlert.setContentText("Your payment has been payed!");
                infoAlert.showAndWait();

                ((Node)(event.getSource())).getScene().getWindow().hide();
            }else {
                errorAlert.setHeaderText("Insufficient balance!");
                errorAlert.setContentText("You don't have enough money to carry out this payment.");
                errorAlert.showAndWait();
            }
        }
    }

}
