package com.example.atm_2;

public interface User {
    String CONNECTION_STRING = "jdbc:mysql://127.0.0.1/atm_final?user=bolu&password=12345&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
    String CLASS_NAME = "com.mysql.cj.jdbc.Driver";

    boolean isAuthenticated(String PIN);


}
