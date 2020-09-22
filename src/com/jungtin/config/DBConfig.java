package com.jungtin.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConfig {
    
    public static Connection getConn() {
        String hostName = "localhost";
        String sqlInstanceName = "SQLEXPRESS";
        String database = "PRJ321_DB";
        String userName = "admin";
        String password = "admin";
        
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    
            String connectionURL = "jdbc:sqlserver://" + hostName + ":1433"
                + ";instance=" + sqlInstanceName + ";databaseName=" + database;
    
            conn = DriverManager.getConnection(connectionURL, userName,
                password);
        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        
        return conn;
    }
}
