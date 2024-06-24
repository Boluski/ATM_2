package com.example.atm_2;

public class Saving implements Account{
    private final String tag = "Saving";
    private String name;
    private float balance;
    private int databaseCode = 2;

    // Creates a savings instance.
    public Saving(String name, float balance){
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
        return String.format("%s (%s)", name, "Savings");
    }

    // Returns the database code.
    @Override
    public int getDbCode(){
        return this.databaseCode;
    }

    // Returns the name of the account.
    @Override
    public String getName(){
        return this.name;
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

    // Returns the account balance.
    @Override
    public float getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        return String.format("Saving: %s-%.2f", this.name, this.balance);
    }
}
