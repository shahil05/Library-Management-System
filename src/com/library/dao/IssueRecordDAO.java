package com.library.dao;

import com.library.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class IssueRecordDAO {

    public boolean issueBook(int bookId, int userId) {
        String checkAvailabilitySql = "SELECT available_copies FROM books WHERE book_id = ? FOR UPDATE";
        String insertIssueSql = "INSERT INTO issue_records (book_id, user_id, issue_date, due_date, status) " +
                "VALUES (?, ?, ?, ?, 'ISSUED')";
        String updateBookSql = "UPDATE books SET available_copies = available_copies - 1 WHERE book_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement checkStmt = conn.prepareStatement(checkAvailabilitySql)) {
                checkStmt.setInt(1, bookId);
                var rs = checkStmt.executeQuery();
                if (!rs.next() || rs.getInt("available_copies") <= 0) {
                    conn.rollback();
                    return false;
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertIssueSql)) {
                insertStmt.setInt(1, bookId);
                insertStmt.setInt(2, userId);
                insertStmt.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                insertStmt.setDate(4, java.sql.Date.valueOf(LocalDate.now().plusDays(14)));
                insertStmt.executeUpdate();
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(updateBookSql)) {
                updateStmt.setInt(1, bookId);
                updateStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Transaction rolled back due to: " + e.getMessage());
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean returnBook(int issueId, int bookId) {
        String updateIssueSql = "UPDATE issue_records SET status = 'RETURNED' WHERE issue_id = ?";
        String updateBookSql = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";
        String insertHistorySql = "INSERT INTO borrowing_history (book_id, user_id, issue_date, due_date, return_date) " +
                "SELECT book_id, user_id, issue_date, due_date, ? FROM issue_records WHERE issue_id = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement historyStmt = conn.prepareStatement(insertHistorySql)) {
                historyStmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                historyStmt.setInt(2, issueId);
                historyStmt.executeUpdate();
            }

            try (PreparedStatement updateIssueStmt = conn.prepareStatement(updateIssueSql)) {
                updateIssueStmt.setInt(1, issueId);
                updateIssueStmt.executeUpdate();
            }

            try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookSql)) {
                updateBookStmt.setInt(1, bookId);
                updateBookStmt.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}