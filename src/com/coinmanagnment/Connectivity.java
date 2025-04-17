package com.coinmanagnment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectivity {


    public static Connection getConnectivity() throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/coin_db", "root", "Mysql@6261");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }}
