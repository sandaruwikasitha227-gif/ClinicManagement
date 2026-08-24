/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sunrise_dental";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    private static Connection connection;

    private DBConnection() {
    }

    public static Connection getConnection() {

        try {

            if (connection == null || connection.isClosed()) {

                Class.forName("com.mysql.cj.jdbc.Driver");

                connection = DriverManager.getConnection(
                        URL,
                        USER,
                        PASSWORD
                );
            }

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println("Database Connection Error: "
                    + e.getMessage());
        }

        return connection;
    }
}
