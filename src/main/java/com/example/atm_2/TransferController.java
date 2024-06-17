/**
 * Sample Skeleton for 'Untitled.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TransferController {

    @FXML // fx:id="amountTextField"
    private TextField amountTextField; // Value injected by FXMLLoader

    @FXML // fx:id="fromCombo"
    private ComboBox<?> fromCombo; // Value injected by FXMLLoader

    @FXML // fx:id="toCombo"
    private ComboBox<?> toCombo; // Value injected by FXMLLoader

    @FXML // fx:id="transferButton"
    private Button transferButton; // Value injected by FXMLLoader

    @FXML
    void handleChange(KeyEvent event) {

    }

    @FXML
    void handleTransfer(ActionEvent event) {

    }

}
