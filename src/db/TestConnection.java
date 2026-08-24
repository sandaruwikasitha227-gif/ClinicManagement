/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/sunrise_dental";
        String user = "root";
        String password = "root";

        try {

            Connection connection =
                    DriverManager.getConnection(url, user, password);

            System.out.println("Database connected successfully!");

            connection.close();

        } catch (Exception e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();

        }
    }
}