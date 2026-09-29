package com.library.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        try {
            Properties props = new Properties();
            InputStream input = DBConnection.class.getClassLoader()
                    .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException(
                        "db.properties not found. Copy db.properties.example to " +
                                "db.properties and fill in your real MySQL password.");
            }

            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage(), e);
        }
    }
}