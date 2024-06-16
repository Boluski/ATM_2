package com.example.atm_2;
/**
 * Sample Skeleton for 'Login.fxml' Controller Class
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

import java.util.Stack;

public class LoginController {

    @FXML // fx:id="PINInput"
    private PasswordField PINInput; // Value injected by FXMLLoader

    @FXML // fx:id="adminRadio"
    private RadioButton adminRadio; // Value injected by FXMLLoader

    @FXML // fx:id="clientCodeInput"
    private TextField codeInput; // Value injected by FXMLLoader

    @FXML // fx:id="clientRadio"
    private RadioButton clientRadio; // Value injected by FXMLLoader

//    @FXML // fx:id="errorMessageText"
//    private Text errorMessageText; // Value injected by FXMLLoader

    @FXML // fx:id="loginButton"
    private Button loginButton; // Value injected by FXMLLoader

    @FXML // fx:id="userToggleGroop"
    private ToggleGroup userToggleGroop; // Value injected by FXMLLoader

    private Alert errorAlert = new Alert(AlertType.ERROR);
    private Client currentUser;
    private int attempts = 0;

    public void initialize() {
        System.out.println("Rendered");
    }

    @FXML
    void handleLogin(ActionEvent event) {
        System.out.println("Login Clicked!");

        String userCode = codeInput.getText();
        String userPIN = PINInput.getText();
        boolean asClient = clientRadio.isSelected();

        if (userCode.isEmpty() || userPIN.isEmpty()) {
            errorAlert.setHeaderText("Missing Fields");
            errorAlert.setContentText("Enter a Code and a PIN.");
            errorAlert.showAndWait();
        } else {
            if (userPIN.length() == 4){
                if(asClient){
                    if (Client.canConnectToServer()){
                        if (Client.isClient(userCode)){
                            currentUser = new Client(userCode);
                            System.out.println(currentUser);

                            if (attempts < 3){
                                if(!currentUser.isBlocked()){
                                    if(currentUser.isAuthenticated(userPIN)){
                                        // TODO Redirect to new page.
                                        System.out.println("User is authenticated");
                                        FXMLLoader root;
                                        try {
                                            root = new FXMLLoader(ATM.class.getResource("Client.fxml"));
                                            Scene scene = new Scene(root.load());


                                            ClientController controller = root.getController();
                                            controller.setData(currentUser);

                                            Stage stage = new Stage();
                                            stage.setTitle("ATM - Client");
//                                            stage.setUserData(currentUser);
                                            stage.setScene(scene);
                                            stage.show();

                                            ((Node)(event.getSource())).getScene().getWindow().hide();

                                        }catch (IOException e){
                                            e.printStackTrace();
                                        }

                                    }else {
                                        attempts++;
                                        errorAlert.setHeaderText("Wrong PIN");
                                        errorAlert.setContentText("The PIN you entered is incorrect.");
                                        errorAlert.showAndWait();


                                    }
                                }else{
                                    errorAlert.setHeaderText("Blocked Client");
                                    errorAlert.setContentText("Your account is blocked please contact your bank.");
                                    errorAlert.showAndWait();

                                }
                            }else {
                                currentUser.setAccountAsBlock(true);
                                attempts = 0;
                                errorAlert.setHeaderText("Blocked Client");
                                errorAlert.setContentText("Your account has been blocked please contact your bank.");
                                errorAlert.showAndWait();
                            }


                        }else {
                            errorAlert.setHeaderText("No Such Client");
                            errorAlert.setContentText("This Client does not exist.");
                            errorAlert.showAndWait();
                        }

                    }else {
                        errorAlert.setHeaderText("Server Error");
                        errorAlert.setContentText("Sorry we could not connect to the server.");
                        errorAlert.showAndWait();

                    }

                }
            }else {
                errorAlert.setHeaderText("PIN format");
                errorAlert.setContentText("PIN must be only four characters.");
                errorAlert.showAndWait();
            }



        }

    }

}
