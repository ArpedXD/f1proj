package com.example.demo.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class highscores extends databasestart {

    public static List<Map<String, Object>> getAllHighscores() {
        List<Map<String, Object>> results = new ArrayList<>();

        String sql = "SELECT u.username, g.game_name, us.wins, us.losses " +
                "FROM user_stats us " +
                "JOIN users u ON us.user_id = u.user_id " +
                "JOIN games g ON us.game_id = g.game_id " +
                "ORDER BY us.wins DESC, us.losses ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("username", rs.getString("username"));
                row.put("gameName", rs.getString("game_name"));
                row.put("wins",     rs.getInt("wins"));
                row.put("losses",   rs.getInt("losses"));
                results.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static List<Map<String, Object>> getHighscoresByGame(String game) {
        List<Map<String, Object>> results = new ArrayList<>();

        String sql = "SELECT u.username, g.game_name, us.wins, us.losses " +
                "FROM user_stats us " +
                "JOIN users u ON us.user_id = u.user_id " +
                "JOIN games g ON us.game_id = g.game_id " +
                "WHERE g.game_name = ? " +
                "ORDER BY us.wins DESC, us.losses ASC";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, game);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                row.put("username", rs.getString("username"));
                row.put("gameName", rs.getString("game_name"));
                row.put("wins",     rs.getInt("wins")); 
                row.put("losses",   rs.getInt("losses"));
                results.add(row);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }
}

public class highscore extends highscores{
    public static List<Map<String, Object>> getAllHighscores() {
        return highscores.getAllHighscores();
    }

    public static List<Map<String, Object>> getHighscoresByGame(String game) {
        return highscores.getHighscoresByGame(game);
    }
}
