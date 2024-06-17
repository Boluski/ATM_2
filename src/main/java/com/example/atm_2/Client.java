package com.example.atm_2;

import java.sql.*;
import java.util.ArrayList;

public class Client implements User {
    private String code;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String PIN;
    private int isBlocked;
    private ArrayList<Account> accounts = new ArrayList<>();

    public Client(String code){
        String query =
                String.format("select * from Clients where Clients.clientCode = \"%s\" ", code);
        String accountQuery =
                String.format("select accountType, accountName, balance from Accounts where accountOwner=\"%s\"", code);
        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()){
                this.code = rs.getString(1);
                this.fullName = rs.getString(2);
                this.phoneNumber = rs.getString(3);
                this.email = rs.getString(4);
                this.PIN = rs.getString(5);
                this.isBlocked = rs.getInt(6);
            }

            rs = stmt.executeQuery(accountQuery);

            while (rs.next()){
                int accountType = rs.getInt(1);
                String accountName = rs.getString(2);
                float balance = rs.getFloat(3);

                Account account = switch (accountType) {
                    case 2 -> new Saving(accountName, balance);
                    case 3 -> new Mortgage(accountName, balance);
                    case 4 -> new LineOfCredit(accountName, balance);
                    default -> new Checking(accountName, balance);
                };
                this.accounts.add(account);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isBlocked(){
        return this.isBlocked == 1;
    }

    public void setAccountAsBlock(boolean status){
        if (status){
            this.isBlocked = 1;
        }else {
            this.isBlocked = 0;
        }

        String query = String.format("update Clients set blocked = %x where clientCode = \"%s\"",
                this.isBlocked, this.code );
        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
               stmt.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isAuthenticated(String PIN){
        return this.PIN.equals(PIN);
    }

    public String getEmail(){
        return this.email;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getPhoneNumber(){
        return String.format("(%s) %s-%s",
                this.phoneNumber.substring(0, 3),
                this.phoneNumber.substring(3, 6),
                this.phoneNumber.substring( 6));
    }

    public boolean asCheckingAccount(){
//        String query = String.format("select * from Accounts where accountOwner = \"%s\" and accountType = 1", this.code);
//
//        try {
//            Class.forName(CLASS_NAME);
//            Connection con = DriverManager.getConnection(CONNECTION_STRING);
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            boolean result = rs.next();
//
//            return result;
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        if (this.accounts.isEmpty()) {
            return false;
        }else {

        }

        return false;
    }

    public boolean asLineOfCreditAccount(){
//        String query = String.format("select * from Accounts where accountOwner = \"%s\" and accountType = 4", this.code);
//
//        try {
//            Class.forName(CLASS_NAME);
//            Connection con = DriverManager.getConnection(CONNECTION_STRING);
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            boolean result = rs.next();
//
//            return result;
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        return false;
    }

    public void addCheckingAccount(String name){
        String query = String.format(
                        "insert into Accounts(accountOwner, AccountType, accountName, balance)\n" +
                        "values(\"%s\", 1, \"%s\", 0)",
                        this.code, name );
        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Checking account = new Checking(name, 0);
        this.accounts.add(account);

        System.out.println(account);


    }

    public void addSavingAccount(String name){
        String query = String.format(
                "insert into Accounts(accountOwner, AccountType, accountName, balance)\n" +
                        "values(\"%s\", 2, \"%s\", 0)",
                this.code, name );
        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Saving account = new Saving(name, 0);
        this.accounts.add(account);

        System.out.println(account);
    }

    public void addMortgageAccount(String name){
        String query = String.format(
                "insert into Accounts(accountOwner, AccountType, accountName, balance)\n" +
                        "values(\"%s\", 3, \"%s\", 0)",
                this.code, name );
        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        Mortgage account = new Mortgage(name, 0);
        this.accounts.add(account);

        System.out.println(account);

    }

    public void addLineOfCreditAccount(String name){
        String query = String.format(
                "insert into Accounts(accountOwner, AccountType, accountName, balance)\n" +
                        "values(\"%s\", 4, \"%s\", 0)",
                this.code, name );
        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        LineOfCredit account = new LineOfCredit(name, 0);
        this.accounts.add(account);

        System.out.println(account);
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

    public static boolean isClient(String code){
//        String query = String.format("select * from Clients where Clients.clientCode = \"%s\" ", code);
        String query = String.format("select clientCode from Clients where Clients.clientCode = \"%s\" ", code);

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
        return String.format("Client: %s-%s-%s-%s-%s-%x",
                code, fullName, phoneNumber, email, PIN, isBlocked);
    }
}
