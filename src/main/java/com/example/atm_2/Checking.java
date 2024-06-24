package com.example.atm_2;

public class Checking implements Account{
    private final String tag = "Checking";
    private String name;
    private float balance;
    private int databaseCode = 1;

    // Creates a checking instance.
    public Checking(String name, float balance){
        this.name = name;
        this.balance = balance;
    }

    // Returns the tag.
    @Override
    public String getTag(){
        return tag;
    }

    // Returns the name of th account with its type.
    @Override
    public String getSelectableName(){
        return String.format("%s (%s)", name, "Checking");
    }

    // Returns the database code.
    @Override
    public int getDbCode(){
        return this.databaseCode;
    }

    // Adds money to the account.
    @Override
    public void addMoney(float amount){
        this.balance += amount;
    };

    // Removes money from the account.
    @Override
    public void removeMoney(float amount){
        this.balance -= amount;
    };

    // Returns the name of the account.
    @Override
    public String getName(){
        return this.name;
    }

    // Returns the account balance.
    @Override
    public float getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return String.format("Checking: %s-%.2f", this.name, this.balance);
    }
}
