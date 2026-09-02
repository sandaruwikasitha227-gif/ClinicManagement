/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import db.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    // =========================
    // LOGIN
    // =========================
    public User login(String username, String password) {

        String sql = "SELECT * FROM users "
                + "WHERE username = ? "
                + "AND password = ? "
                + "AND status = 'ACTIVE'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setStatus(rs.getString("status"));

                return user;
            }

        } catch (Exception e) {

            System.out.println(
                    "Login Error: " + e.getMessage()
            );
        }

        return null;
    }


    // =========================
    // CHECK USERNAME
    // =========================
    public boolean usernameExists(String username)
            throws Exception {

        String sql =
                "SELECT user_id FROM users "
                + "WHERE username = ?";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, username);

            try (ResultSet rs =
                         pst.executeQuery()) {

                return rs.next();
            }
        }
    }


    // =========================
    // ADD USER
    // =========================
    public boolean addUser(
            String username,
            String password,
            String role,
            String status) throws Exception {

        String sql =
                "INSERT INTO users "
                + "(username, password, role, status) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con =
                     DBConnection.getConnection();
             PreparedStatement pst =
                     con.prepareStatement(sql)) {

            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, role);
            pst.setString(4, status);

            return pst.executeUpdate() > 0;
        }
    }


    // =========================
    // GET ALL USERS
    // =========================
    public ResultSet getAllUsers()
            throws Exception {

        String sql =
                "SELECT user_id, username, role, status "
                + "FROM users "
                + "ORDER BY user_id";

        Connection con =
                DBConnection.getConnection();

        PreparedStatement pst =
                con.prepareStatement(sql);

        return pst.executeQuery();
    }
}