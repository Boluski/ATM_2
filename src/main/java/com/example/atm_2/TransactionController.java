/**
 * Sample Skeleton for 'Transaction.fxml' Controller Class
 */

package com.example.atm_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class TransactionController {

    @FXML // fx:id="IDColumn"
    private TableColumn<Transaction, String> IDColumn; // Value injected by FXMLLoader

    @FXML // fx:id="LOCColumn"
    private TableColumn<Transaction, String> LOCColumn; // Value injected by FXMLLoader

    @FXML // fx:id="adminColumn"
    private TableColumn<Transaction, String> adminColumn; // Value injected by FXMLLoader

    @FXML // fx:id="amountColumn"
    private TableColumn<Transaction, String> amountColumn; // Value injected by FXMLLoader

    @FXML // fx:id="balanceCulumn"
    private TableColumn<Transaction, String> moveToColumn;

    @FXML // fx:id="balanceCulumn"
    private TableColumn<Transaction, String> balanceCulumn; // Value injected by FXMLLoader

    @FXML // fx:id="billColumn"
    private TableColumn<Transaction, String> billColumn; // Value injected by FXMLLoader

    @FXML // fx:id="dateColumn"
    private TableColumn<Transaction, String> dateColumn; // Value injected by FXMLLoader

    @FXML // fx:id="transactionTable"
    private TableView<Transaction> transactionTable; // Value injected by FXMLLoader

    ObservableList<Transaction> data = FXCollections.observableArrayList();

    void setData(ArrayList<Transaction> transactions){
        data.addAll(transactions);

        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        LOCColumn.setCellValueFactory(new PropertyValueFactory<>("LOC"));
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("Admin"));

        amountColumn.setCellValueFactory(new PropertyValueFactory<>("Amount"));

        moveToColumn.setCellValueFactory(new PropertyValueFactory<>("MovedToAccount"));

        balanceCulumn.setCellValueFactory(new PropertyValueFactory<>("Balance"));

        billColumn.setCellValueFactory(new PropertyValueFactory<>("Bill"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));

        transactionTable.setItems(data);
    }
}
