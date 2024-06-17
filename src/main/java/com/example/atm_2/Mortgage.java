package com.example.atm_2;

public class Mortgage implements Account{
    public final String tag = "Mortgage";
    public String name;
    public float balance;

    public Mortgage(String name, float balance){
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String getTag(){
        return tag;
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
        return String.format("Mortgage: %s-%.2f", this.name, this.balance);
    }
}
