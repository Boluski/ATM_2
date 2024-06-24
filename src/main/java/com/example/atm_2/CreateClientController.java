/**
 * Sample Skeleton for 'CreateClient.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CreateClientController {

    @FXML // fx:id="PINTextField"
    private TextField PINTextField; // Value injected by FXMLLoader

    @FXML // fx:id="clientCodeTextField"
    private TextField clientCodeTextField; // Value injected by FXMLLoader

    @FXML // fx:id="createButton"
    private Button createButton; // Value injected by FXMLLoader

    @FXML // fx:id="emailTextField"
    private TextField emailTextField; // Value injected by FXMLLoader

    @FXML // fx:id="fullNameTextField"
    private TextField fullNameTextField; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberTextField"
    private TextField phoneNumberTextField; // Value injected by FXMLLoader

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Admin currentAdmin;
    void setData(Admin admin){
        currentAdmin = admin;
    }
    
    @FXML
    void handleChange(KeyEvent event) {

    }

    @FXML
    void handleClinetCode(ActionEvent event) {
        
    }

    @FXML
    void handleCreate(ActionEvent event) {
        String clientCode = clientCodeTextField.getText();
        String clientPIN = PINTextField.getText();
        String fullName = fullNameTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String email = emailTextField.getText();
        
        if (clientCode.isEmpty() || clientPIN.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty()){
            errorAlert.setHeaderText("Empty fields!");
            errorAlert.setContentText("Some fields are empty!");
            errorAlert.showAndWait();
        }else {
            Client newClient = Client.createClient(clientCode, clientPIN, fullName, phoneNumber, email);
            currentAdmin.addClient(newClient);

            ((Node)(event.getSource())).getScene().getWindow().hide();
        }
    }

    @FXML
    void handleEmail(ActionEvent event) {

    }

    @FXML
    void handleFullName(ActionEvent event) {

    }

    @FXML
    void handlePIN(ActionEvent event) {

    }

    @FXML
    void handlePhoneNumber(ActionEvent event) {

    }

}
