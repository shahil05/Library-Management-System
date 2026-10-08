package com.library.dao;

import com.library.util.DBConnection;
import java.sql.*;

public class UserDAO {

    // Returns the user's role if email+password match, or null if not.
    // In a real product, passwords would be hashed -- we're keeping this
    // simple and plaintext since that's a separate topic from JSP/sessions.
    public String login(String email, String password) {
        String sql = "SELECT role FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // no match
    }

    public int getUserIdByEmail(String email) {
        String sql = "SELECT user_id FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("user_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}