package com.example.demo.database;

import com.example.demo.base.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class account extends databasestart {

    public static String register(String username, String password, String gmail) throws SQLException {
        String checkIfExist = "SELECT 1 FROM users WHERE username = ? OR email = ?";
        String insertUser   = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)";
        String insertStats  = "INSERT INTO user_stats (user_id, game_id) SELECT ?, game_id FROM games";

        try (Connection conn = getConnection()) {

            // 1. Check if user exists
            try (PreparedStatement stmt = conn.prepareStatement(checkIfExist)) {
                stmt.setString(1, username);
                stmt.setString(2, gmail);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) return "User already exists!";
            }

            int userId;

            // 2. Insert user
            try (PreparedStatement stmt = conn.prepareStatement(insertUser, PreparedStatement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, username);
                stmt.setString(2, gmail);
                stmt.setString(3, password);
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    userId = rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve user ID");
                }
            }

            // 3. Insert stats
            try (PreparedStatement stmt = conn.prepareStatement(insertStats)) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }

            System.out.println("lol");
            return "Success!";
        }
    }

    public static String Login(String username, String password) throws SQLException {
        String sql = "SELECT password_hash FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (storedHash.equals(password)) {
                    base.username = username;
                    base.loggedin = true;
                    return "Login Success!";
                }
            }
        }
        return "Wrong Password or Username";
    }

    // ── New: get email by username (used for login OTP) ─────────────────────
    public static String getEmailByUsername(String username) throws SQLException {
        String sql = "SELECT email FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("email");
            return "";
        }
    }

    // ── Nfew: check if an email is registered ─────────────────────────────
    public static boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }

    // ── New: update password by email ────────────────────────────────────
    public static String changePassword(String email, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password_hash = ? WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newPassword);
            stmt.setString(2, email);
            int rows = stmt.executeUpdate();

            if (rows > 0) return "Password updated successfully!";
            return "Email not found";
        }
    }
}