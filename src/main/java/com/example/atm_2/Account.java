package com.example.atm_2;

public interface Account {
    float getBalance();
    void addMoney(float amount);
    void removeMoney(float amount);
    String getTag();
    String getSelectableName();
    String getName();
    int getDbCode();
}
