/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Vikasitha
 */
public class Session {

    private static int userId;
    private static String username;
    private static String role;

    private Session() {
    }

    public static void startSession(
            int id,
            String user,
            String userRole) {

        userId = id;
        username = user;
        role = userRole;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    public static boolean isLoggedIn() {
        return username != null
                && !username.trim().isEmpty();
    }

    public static void logout() {

        userId = 0;
        username = null;
        role = null;
    }
}
