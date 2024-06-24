package com.example.atm_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class Admin implements User{
    private String code;
    private String PIN;
    private float paperMoney;
    private ArrayList<Client> allClients = new  ArrayList<>();
    public ObservableList<String> Clients = FXCollections.observableArrayList();

    // Creates an admin instance and gets all the data that are requires form the database.
    public Admin(String code){
        String query =
                String.format("select * from Admins where Admins.adminCode = \"%s\" ", code);
        String clientQuery = "select clientCode from Clients";
        String paperMoneyQuery =
                String.format("select balance from Cash where cashID = 1");

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()){
                this.code = rs.getString(1);
                this.PIN = rs.getString(2);
            }

            rs = stmt.executeQuery(clientQuery);

            while (rs.next()){
                Client indClient = new Client(rs.getString(1));
                this.allClients.add(indClient);
                this.Clients.add(indClient.toString());
            }

            rs = stmt.executeQuery(paperMoneyQuery);

            if (rs.next()){
                this.paperMoney = rs.getFloat(1);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // Returns the admin code
    public String getCode(){
        return this.code;
    }

    // Returns true if the admin is authenticated. If the password match what was given.
    public boolean isAuthenticated(String PIN){
        return this.PIN.equals(PIN);
    }

    // Returns the paper money.
    public float getPaperMoney(){
        return this.paperMoney;
    }

    // Sets the paper money.
    public void setPaperMoney(float money){
        this.paperMoney = money;

        String paperQuery = String.format("update Cash\n" +
                "set balance = %.2f where cashID = 1", this.paperMoney);

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(paperQuery);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // Adds 1% bonus to all savings account.
    public void addBonusToAllSavings(){
        for (Client indClient: this.allClients){
            indClient.addSavingsBonus(this.code);
        }
    }

    // Adds a Client object to the observable array.
    public void addClient(Client newClient){
        this.allClients.add(newClient);
        this.Clients.add(newClient.toString());
    }

    // Add a 5% interest to all line of credits.
    public void addInterest(String admin){
        for (Client indClient: this.allClients){
            indClient.addLOCInterest(admin);
        }
    }

    // Returns the first client in the array.
    public Client getClient(){
        return this.allClients.getFirst();
    }

    // Returns the client based on the client name.
    public Client getClient(String name){

        for (Client indClient: this.allClients){
            if(indClient.toString().equals(name)){
                return indClient;
            }
        }
        return this.allClients.getFirst();
    }

    // Returns true if it can connect to the server.
    public static boolean canConnectToServer(){
        try {
            Class.forName(CLASS_NAME);
            DriverManager.getConnection(CONNECTION_STRING);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // Returns true if the code is in the database.
    public static boolean isAdmin(String code){
//        String query = String.format("select * from Clients where Clients.clientCode = \"%s\" ", code);
        String query = String.format("select adminCode from Admins where Admins.adminCode = \"%s\" ", code);

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            boolean result = rs.next();

            return result;

        }catch (Exception e){
            e.printStackTrace();
        }


        return false;
    }

    @Override
    public String toString() {
        return String.format("Client: %s-%s", code,  PIN);
    }
}
