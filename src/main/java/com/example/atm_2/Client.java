package com.example.atm_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;


public class Client implements User {
    private String code;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String PIN;
    private int isBlocked;
    private float grandTotal;

    private float paperMoney;

    public SimpleStringProperty observableGrandTotal = new SimpleStringProperty("$0.00");
    public ObservableList<String> observableCheckingAndSaving = FXCollections.observableArrayList();
    public ObservableList<String> observableAllAccount = FXCollections.observableArrayList();

    private LineOfCredit LOCAccount = null;

    private final ArrayList<Account> accounts = new ArrayList<>();
    private final ArrayList<Account> canDepositAccount = new ArrayList<>();
    private final ArrayList<Account> allSavingsAndCheckingAccount = new ArrayList<>();
    private final ArrayList<Checking> allCheckingAccount = new ArrayList<>();

    public Client(String code){
        String query =
                String.format("select * from Clients where Clients.clientCode = \"%s\" ", code);
        String accountQuery =
                String.format("select accountType, accountName, balance from Accounts where accountOwner=\"%s\"", code);
        String paperMoneyQuery =
                String.format("select * from Cash");

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

            rs = stmt.executeQuery(paperMoneyQuery);

            if (rs.next()){
                this.paperMoney = rs.getFloat(1);
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

                boolean isLOC = false;
                if (!account.getTag().equals("LineOfCredit")){
                    this.canDepositAccount.add(account);

                    if (account.getTag().equals("Checking")){
                        this.allCheckingAccount.add((Checking) account);
                        this.allSavingsAndCheckingAccount.add(account);
                        observableCheckingAndSaving.add(account.getSelectableName());
                    }

                    if (account.getTag().equals("Saving")){
                        this.allSavingsAndCheckingAccount.add(account);
                        observableCheckingAndSaving.add(account.getSelectableName());
                    }

                }else {
                    this.LOCAccount = (LineOfCredit) account;
                    this.grandTotal -= account.getBalance();
                    isLOC = true;
                }

                if(!isLOC){
                    this.grandTotal += account.getBalance();
                }
                this.accounts.add(account);
                this.observableAllAccount.add(account.getSelectableName());
            }

            this.observableGrandTotal.set(this.getGrandTotal());

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

    public boolean asPaperMoney(float amount){
        return amount <= this.paperMoney;
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
        this.observableAllAccount.add(account.getSelectableName());
        this.allCheckingAccount.add(account);
        this.canDepositAccount.add(account);
        this.allSavingsAndCheckingAccount.add(account);
        observableCheckingAndSaving.add(account.getSelectableName());

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
        this.observableAllAccount.add(account.getSelectableName());
        this.canDepositAccount.add(account);
        this.allSavingsAndCheckingAccount.add(account);
        observableCheckingAndSaving.add(account.getSelectableName());

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
        this.observableAllAccount.add(account.getSelectableName());
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
        this.observableAllAccount.add(account.getSelectableName());
        this.LOCAccount = account;

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

    public ArrayList<String> getAllBalance(){
        ArrayList<String> result = new ArrayList<>();

        for (Account account: this.accounts){
            String accountBalance = String.format("$%.2f", account.getBalance());
            Character c = accountBalance.charAt(1);
            if (c.equals('-')){
                accountBalance = accountBalance.replace("$-", "-$");
            }
            result.add(accountBalance);
        }

        return result;
    }

    public String getGrandTotal(){
        String accountBalance = String.format("$%.2f", this.grandTotal);
        Character c = accountBalance.charAt(1);
        if (c.equals('-')){
            accountBalance = accountBalance.replace("$-", "-$");
        }

        return accountBalance;
    }

    public boolean withdraw(String accountName, float amount){
        DecimalFormat df = new DecimalFormat("#.00");
        float fAmount = Float.parseFloat(df.format(amount));
        boolean result = false;

        Account account = null;

        for (Account indAccount: this.allSavingsAndCheckingAccount){
            if (indAccount.getSelectableName().equals(accountName)){
                if (indAccount.getBalance() >= fAmount){
                    indAccount.removeMoney(fAmount);
                    this.paperMoney -= fAmount;
                    this.grandTotal -= fAmount;
                    account = indAccount;
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

        String paperQuery = String.format("update Cash\n" +
                "set balance = %.2f", this.paperMoney);

        String transactionQuery = String.format(
                        "insert into Transaction(client, accountType, amount, transactionType, balance)\n" +
                        "values(\"%s\", %x, %.2f, 2, %.2f)", this.code, account.getDbCode(), fAmount, account.getBalance());

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
                stmt.executeUpdate(paperQuery);
                stmt.executeUpdate(transactionQuery);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        observableGrandTotal.set(this.getGrandTotal());
        return result;
    }

    public void withdrawAndUseLineOfCredit(String accountName, float amount){
        DecimalFormat df = new DecimalFormat("#.00");
        float fAmount = Float.parseFloat(df.format(amount));
        float accountFunds = 0;

        Account account = null;

        for (Account indAccount: this.allSavingsAndCheckingAccount){
            if (indAccount.getSelectableName().equals(accountName)){
                accountFunds = indAccount.getBalance();

                this.paperMoney -= fAmount;
                fAmount -= accountFunds;

                indAccount.removeMoney(accountFunds);
                this.grandTotal -= accountFunds;

                this.LOCAccount.removeMoney(fAmount);
                this.grandTotal -= fAmount;

                account = indAccount;

            }
        }


        String query = String.format(
                "update Accounts\n" +
                        "set balance = %.2f\n" +
                        "where accountOwner = \"%s\" and accountName = \"%s\"",
                account.getBalance(), this.code, account.getName());

        String paperQuery = String.format("update Cash\n" +
                "set balance = %.2f", this.paperMoney);

        String transactionQuery = String.format(
                        "insert into Transaction(client, accountType, amount, LOCAmount, transactionType, balance)\n" +
                        "values(\"%s\", %x, %.2f, %.2f, 2, %.2f)", this.code, account.getDbCode(), accountFunds, fAmount, account.getBalance());

        try {
            Class.forName(CLASS_NAME);
            Connection con = DriverManager.getConnection(CONNECTION_STRING);
            Statement stmt = con.createStatement();

            try {
                stmt.executeUpdate(query);
                stmt.executeUpdate(paperQuery);
                stmt.executeUpdate(transactionQuery);
            }catch (SQLException e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        observableGrandTotal.set(this.getGrandTotal());
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
                    this.grandTotal -= fAmount;
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

        observableGrandTotal.set(this.getGrandTotal());
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
                this.grandTotal += fAmount;
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
                        observableGrandTotal.set(this.getGrandTotal());
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

    public void addSavingsBonus(){
        for (Account indSavings: allSavingsAndCheckingAccount){
            if (indSavings.getTag().equals("Saving")){
                float accountBalance = indSavings.getBalance();
                float interest = (float) 0.01;
                float newAccountBalance =  accountBalance + (accountBalance * interest);
                System.out.println(accountBalance);
                System.out.println(newAccountBalance);

                this.deposit(indSavings.getName(), newAccountBalance);
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
