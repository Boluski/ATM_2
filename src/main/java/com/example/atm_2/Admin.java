package com.example.atm_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.chrono.MinguoDate;
import java.util.ArrayList;

public class Admin implements User{
    private String code;
    private String PIN;

    private float paperMoney;

    private ArrayList<Client> allClients = new  ArrayList<>();

    public ObservableList<String> Clients = FXCollections.observableArrayList();



    public Admin(String code){
        String query =
                String.format("select * from Admins where Admins.adminCode = \"%s\" ", code);
        String clientQuery = "select clientCode from Clients";
        String paperMoneyQuery =
                String.format("select * from Cash");

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

//
//            rs = stmt.executeQuery(accountQuery);
//
//            while (rs.next()){
//                int accountType = rs.getInt(1);
//                String accountName = rs.getString(2);
//                float balance = rs.getFloat(3);
//
//                Account account = switch (accountType) {
//                    case 2 -> new Saving(accountName, balance);
//                    case 3 -> new Mortgage(accountName, balance);
//                    case 4 -> new LineOfCredit(accountName, balance);
//                    default -> new Checking(accountName, balance);
//                };
//
//                boolean isLOC = false;
//                if (!account.getTag().equals("LineOfCredit")){
//                    this.canDepositAccount.add(account);
//
//                    if (account.getTag().equals("Checking")){
//                        this.allCheckingAccount.add((Checking) account);
//                        this.allSavingsAndCheckingAccount.add(account);
//                        observableCheckingAndSaving.add(account.getSelectableName());
//                    }
//
//                    if (account.getTag().equals("Saving")){
//                        this.allSavingsAndCheckingAccount.add(account);
//                        observableCheckingAndSaving.add(account.getSelectableName());
//                    }
//
//                }else {
//                    this.LOCAccount = (LineOfCredit) account;
//                    this.grandTotal -= account.getBalance();
//                    isLOC = true;
//                }
//
//                if(!isLOC){
//                    this.grandTotal += account.getBalance();
//                }
//                this.accounts.add(account);
//            }
//
//            this.observableGrandTotal.set(this.getGrandTotal());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isAuthenticated(String PIN){
        return this.PIN.equals(PIN);
    }

    public float getPaperMoney(){
        return this.paperMoney;
    }

    public void setPaperMoney(float money){
        this.paperMoney = money;

        String paperQuery = String.format("update Cash\n" +
                "set balance = %.2f", this.paperMoney);

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

    public void addBonusToAllSavings(){
        for (Client indClient: this.allClients){
            indClient.addSavingsBonus();
        }
    }

    public Client getClient(){
        return this.allClients.getFirst();
    }

    public Client getClient(String name){

        for (Client indClient: this.allClients){
            if(indClient.toString().equals(name)){
                return indClient;
            }
        }
        return this.allClients.getFirst();
    }

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
