package com.example.atm_2;

import java.sql.*;

public class Client {
    private static final String CONNECTION_STRING = "jdbc:mysql://127.0.0.1/atm?user=bolu&password=12345&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    private static final String CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    private String code;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String PIN;
    private int isBlocked;

    public Client(String code){
        String query = String.format("select * from Clients where Clients.clientCode = \"%s\" ", code);
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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isBlocked(){
        return this.isBlocked == 1;
    }

    public void setBlockAccount(boolean status){
        if (status){
            this.isBlocked = 1;
        }else {
            this.isBlocked = 0;
        }


    }

    public boolean isAuthenticated(String PIN){
        return this.PIN.equals(PIN);
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
        return String.format("%s-%s-%s-%s-%s-%x",
                code, fullName, phoneNumber, email, PIN, isBlocked);
    }
}
