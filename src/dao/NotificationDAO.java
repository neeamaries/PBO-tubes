package src.dao;

import src.config.DatabaseConnection;
import src.model.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public boolean insertNotification(Notification notification) {
        String sql = """
                INSERT INTO notifications (user_id, budget_id, message, notification_date)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, notification.getUserID());
            stmt.setInt(2, notification.getBudgetID());
            stmt.setString(3, notification.getMessage());
            stmt.setString(4, notification.getNotificationDate());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        notification.setNotificationID(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert notification: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteNotification(int notificationID) {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notificationID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal delete notification: " + e.getMessage());
        }

        return false;
    }

    public Notification findById(int notificationID) {
        String sql = "SELECT * FROM notifications WHERE notification_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, notificationID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToNotification(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari notification: " + e.getMessage());
        }

        return null;
    }

    public List<Notification> findByUserId(int userID) {
        List<Notification> notifications = new ArrayList<>();

        String sql = """
                SELECT * FROM notifications
                WHERE user_id = ?
                ORDER BY notification_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil notification user: " + e.getMessage());
        }

        return notifications;
    }

    public List<Notification> findByBudgetId(int budgetID) {
        List<Notification> notifications = new ArrayList<>();

        String sql = """
                SELECT * FROM notifications
                WHERE budget_id = ?
                ORDER BY notification_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, budgetID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    notifications.add(mapResultSetToNotification(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil notification budget: " + e.getMessage());
        }

        return notifications;
    }

    private Notification mapResultSetToNotification(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getInt("notification_id"),
                rs.getInt("user_id"),
                rs.getInt("budget_id"),
                rs.getString("message"),
                rs.getString("notification_date")
        );
    }
}