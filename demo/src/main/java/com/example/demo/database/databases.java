package com.example.demo.database;

import java.sql.*;
import java.util.*;

class databasestart{
    private static final String URL = "jdbc:mysql://localhost:3306/game_system";
    private static final String USER = "root";
    private static final String PASSWORD = "Sqzimp0#40...||WOOFLOL";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

class games extends databasestart{
    public static void addWin(String username, String game) {
        String sql = "UPDATE user_stats us " +
                "JOIN users u ON us.user_id = u.user_id " +
                "JOIN games g ON us.game_id = g.game_id " +
                "SET us.wins = us.wins + 1 " +
                "WHERE u.username = ? AND g.game_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, game);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addLose(String username, String game) {
        String sql = "UPDATE user_stats us " +
                "JOIN users u ON us.user_id = u.user_id " +
                "JOIN games g ON us.game_id = g.game_id " +
                "SET us.losses = us.losses + 1 " +
                "WHERE u.username = ? AND g.game_name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, game);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Integer> showstats(String username,String game){
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT us.wins, us.losses " +
                "FROM user_stats us " +
                "JOIN users u ON us.user_id = u.user_id " +
                "JOIN games g ON us.game_id = g.game_id " +
                "WHERE u.username = ? AND g.game_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, game);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                stats.put("wins", rs.getInt("wins"));
                stats.put("losses", rs.getInt("losses"));
            } else {
                stats.put("wins", 0);
                stats.put("losses", 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            stats.put("wins", 0);
            stats.put("losses", 0);
        }

        return stats;
    }
}

public class databases extends games{
    public static void win(String username,String game){
        games.addWin(username, game);
    }

    public static void lose(String username, String game){
        games.addLose(username, game);
    }

    public static Map<String, Integer> stats(String username, String game){
        return games.showstats(username,game);
    }

}

