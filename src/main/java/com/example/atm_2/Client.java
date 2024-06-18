package com.example.atm_2;

import java.math.BigDecimal;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Client implements User {
    private String code;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String PIN;
    private int isBlocked;
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final ArrayList<Account> canDepositAccount = new ArrayList<>();
    private final ArrayList<Checking> allCheckingAccount = new ArrayList<>();

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

                if (!account.getTag().equals("LineOfCredit")){
                    this.canDepositAccount.add(account);

                    if (account.getTag().equals("Checking")){
                        this.allCheckingAccount.add((Checking) account);
                    }

                }

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
        if (this.accounts.isEmpty()) {
            return false;
        }else {
            for (Account account: this.accounts){
                if (account.getTag().equals("Checking")){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean asLineOfCreditAccount(){
        if (this.accounts.isEmpty()) {
            return false;
        }else {
            for (Account account: this.accounts){
                if (account.getTag().equals("LineOfCredit")){
                    return true;
                }
            }
        }
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
        this.allCheckingAccount.add(account);
        this.canDepositAccount.add(account);

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
        this.canDepositAccount.add(account);

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
        this.canDepositAccount.add(account);

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

    public ArrayList<String> getCanDepositAccounts(){
        ArrayList<String> result = new ArrayList<>();

        for (Account account: this.canDepositAccount){
            result.add(account.getSelectableName());
        }

        return result;
    }

    public ArrayList<String> getAllCheckingAccounts(){
        ArrayList<String> result = new ArrayList<>();

        for (Checking checking: this.allCheckingAccount){
            result.add(checking.getName());
        }

        return result;
    }

    public ArrayList<String> getAllAccounts(){
        ArrayList<String> result = new ArrayList<>();

        for (Account account: this.accounts){
            result.add(account.getSelectableName());
        }

        return result;
    }

    public boolean payBill(String accountName, String billName, float amount){
        DecimalFormat df = new DecimalFormat("#.00");
        float fAmount = Float.parseFloat(df.format(amount + 1.25));
        boolean result = false;

        Checking account = null;

        for (Checking indAccount: this.allCheckingAccount){
            if (indAccount.getName().equals(accountName)){
                if (indAccount.getBalance() >= fAmount){

                    System.out.println(indAccount);

                    indAccount.removeMoney(fAmount);
                    account = indAccount;
                    System.out.println(account);

                    result = true;
                }else {
                    return false;
                }
            }
        }

        String query = String.format(
                        "update Accounts\n" +
                        "set balance = %.2f\n" +
                        "where accountOwner = \"%s\" and accountName = \"%s\"",
                        account.getBalance(), this.code, account.getName());

        String transactionQuery = String.format(
                        "insert into Transaction(client, accountType, amount, transactionType, balance, billName)\n" +
                        "values(\"%s\", 1, %.2f, 4, %.2f, \"%s\")", this.code,  fAmount, account.getBalance(), billName);

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
                stmt.executeUpdate(transactionQuery);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public boolean transfer(String from, String to, float amount){
        DecimalFormat df = new DecimalFormat("#.00");
        float fAmount = Float.parseFloat(df.format(amount));
        Checking fromAccount = null;
        Account toAccount = null;
        boolean continueTransfer = false;

        boolean result = false;

        for (Checking indChecking: this.allCheckingAccount ){
            if (indChecking.getName().equals(from)){
                if (indChecking.getBalance() >= fAmount){
                    indChecking.removeMoney(fAmount);
                    fromAccount = indChecking;
                    continueTransfer = true;
                    break;

                }else {
                    return false;
                }

            }
        }

        if (continueTransfer){
            for (Account indAccount: this.accounts ){
                if (indAccount.getSelectableName().equals(to)){
                    indAccount.addMoney(fAmount);
                    toAccount = indAccount;

                    System.out.println(fromAccount);
                    System.out.println(toAccount);

                    result = true;
                }
            }
        }



        String fromQuery = String.format(
                        "update Accounts\n" +
                        "set balance = %.2f\n" +
                        "where accountOwner = \"%s\" and accountName = \"%s\"",
                        fromAccount.getBalance(),this.code, fromAccount.getName());


        String toQuery = String.format(
                        "update Accounts\n" +
                        "set balance = %.2f\n" +
                        "where accountOwner = \"%s\" and accountName = \"%s\"",
                        toAccount.getBalance(),this.code, toAccount.getName());

        String transactionQuery = String.format(
                "insert into Transaction(client, accountType, amount, transactionType, movedToAccountType, balance)\n" +
                        "values(\"%s\", 1, %.2f, 3, %x, %.2f)", this.code,  fAmount, toAccount.getDbCode(), fromAccount.getBalance());

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(fromQuery);
                stmt.executeUpdate(toQuery);
                stmt.executeUpdate(transactionQuery);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void deposit(String accountName, float amount){
        DecimalFormat df = new DecimalFormat("#.00");
        float fAmount = Float.parseFloat(df.format(amount));

        for (Account account: this.canDepositAccount){
            if (account.getSelectableName().equals(accountName)){
                account.addMoney(fAmount);
                System.out.println(account);

                String query = String.format(
                                "update Accounts\n" +
                                "set balance = %.2f\n" +
                                "where accountOwner = \"%s\" and accountName = \"%s\"",
                                account.getBalance(),this.code, account.getName());


                String transactionQuery = String.format(
                        "insert into Transaction(client, accountType, amount, transactionType, balance)\n" +
                        "values(\"%s\", %x, %.2f, 1, %.2f)", this.code, account.getDbCode(), fAmount, account.getBalance());

                try {
                    Class.forName(CLASS_NAME);
                    Connection con = DriverManager.getConnection(CONNECTION_STRING);
                    Statement stmt = con.createStatement();

                    try {
                        stmt.executeUpdate(query);
                        stmt.executeUpdate(transactionQuery);
                    }catch (SQLException e){
                        e.printStackTrace();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                break;
            }
        }

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
