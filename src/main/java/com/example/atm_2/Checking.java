package com.example.atm_2;

public class Checking implements Account{
    public final String tag = "Checking";
    public String name;
    public float balance;

    public Checking(String name, float balance){
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
        return String.format("Checking: %s-%.2f", this.name, this.balance);
    }
}
