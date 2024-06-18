/**
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

public class TransferController {

    @FXML // fx:id="amountTextField"
    private TextField amountTextField; // Value injected by FXMLLoader

    @FXML // fx:id="fromCombo"
    private ComboBox<String> fromCombo; // Value injected by FXMLLoader

    @FXML // fx:id="toCombo"
    private ComboBox<String> toCombo; // Value injected by FXMLLoader

    @FXML // fx:id="transferButton"
    private Button transferButton; // Value injected by FXMLLoader

    private Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Client currentUser;

    public static boolean isAlphabet(String input) {
        return input.matches(".*[A-Za-z].*");
    }

    public void setDate(Client user){
        currentUser = user;

        transferButton.setDisable(true);

        fromCombo.getItems().addAll(currentUser.getAllCheckingAccounts());
        fromCombo.setValue(currentUser.getAllCheckingAccounts().getFirst());

        toCombo.getItems().addAll(currentUser.getAllAccounts());
        toCombo.setValue(currentUser.getAllAccounts().getFirst());
    }

    @FXML
    void handleChange(KeyEvent event) {
        try {
            Character c = amountTextField.getText().charAt(0);
            if (amountTextField.getText().isEmpty() || c.equals('-')){
                transferButton.setDisable(true);
            }else {
                transferButton.setDisable(isAlphabet(amountTextField.getText()));
            }
        }catch (Exception e){

        }
    }

    @FXML
    void handleTransfer(ActionEvent event) {
        System.out.println(fromCombo.getValue());
        System.out.println(toCombo.getValue());

        String from = fromCombo.getValue();
        String to = toCombo.getValue();

        String toSub = to.substring(0, from.length());
        float amount = Float.parseFloat(amountTextField.getText());

        if (from.equals(toSub)){
            errorAlert.setHeaderText("Same account!");
            errorAlert.setContentText("You can't transfer money to the same account! Use a different account.");
            errorAlert.showAndWait();
        }else {

            if (currentUser.transfer(from, to, amount)){
                infoAlert.setHeaderText("Transferred!");
                infoAlert.setContentText("Your money has been transferred to the new account!");
                infoAlert.showAndWait();

                ((Node)(event.getSource())).getScene().getWindow().hide();
            }else {
                errorAlert.setHeaderText("Insufficient balance!");
                errorAlert.setContentText("You don't have enough money to carry out this transfer.");
                errorAlert.showAndWait();
            }

        }




    }

}
