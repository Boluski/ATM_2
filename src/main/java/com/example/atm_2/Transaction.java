package com.example.atm_2;

public class Transaction {
    private String ID;
    private String LOC;
    private String admin;
    private String amount;
    private String movedToAccount;
    private String balance;
    private String bill;
    private String date;

    public Transaction(String ID, String LOC, String admin, String amount, String movedToAccount, String balance, String bill, String date) {
        this.ID = ID;
        this.LOC = LOC;
        this.admin = admin;
        this.amount = amount;
        this.balance = balance;
        this.movedToAccount = movedToAccount;
        this.bill = bill;
        this.date = date;
    }

    public String getMovedToAccount() {
        return this.movedToAccount;
    }

    public void setMovedToAccount(String movedToAccount) {
        this.movedToAccount = movedToAccount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLOC() {
        return LOC;
    }

    public void setLOC(String LOC) {
        this.LOC = LOC;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
