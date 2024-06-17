package com.example.atm_2;

public class LineOfCredit implements Account{
    public final String tag = "LineOfCredit";
    public String name;
    public float balance;

    public LineOfCredit(String name, float balance){
        this.name = name;
        this.balance = balance;
    }

    @Override
    public void addMoney(float amount){
        this.balance += amount;
    };

    @Override
    public float getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return String.format("Line Of Credit: %s-%.2f", this.name, this.balance);
    }
}
