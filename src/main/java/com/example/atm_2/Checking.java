package com.example.atm_2;

public class Checking implements Account{
    private final String tag = "Checking";
    private String name;
    private float balance;
    private int databaseCode = 1;

    public Checking(String name, float balance){
        this.name = name;
        this.balance = balance;
    }

    @Override
    public String getTag(){
        return tag;
    }

    @Override
    public String getSelectableName(){
        return String.format("%s (%s)", name, "Checking");
    }

    @Override
    public int getDbCode(){
        return this.databaseCode;
    }

    @Override
    public void addMoney(float amount){
        this.balance += amount;
    };

    @Override
    public void removeMoney(float amount){
        this.balance -= amount;
    };

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public float getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return String.format("Checking: %s-%.2f", this.name, this.balance);
    }
}
