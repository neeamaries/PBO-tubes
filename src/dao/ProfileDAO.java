package src.dao;

import src.config.DatabaseConnection;
import src.model.Profile;

import java.sql.*;

public class ProfileDAO {

    public boolean insertProfile(Profile profile) {
        String sql = """
                INSERT INTO profiles (user_id, full_name, phone_number, address)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, profile.getUserID());
            stmt.setString(2, profile.getFullName());
            stmt.setString(3, profile.getPhoneNumber());
            stmt.setString(4, profile.getAddress());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        profile.setProfileID(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert profile: " + e.getMessage());
        }

        return false;
    }

    public boolean updateProfile(Profile profile) {
        String sql = """
                UPDATE profiles
                SET full_name = ?, phone_number = ?, address = ?
                WHERE user_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profile.getFullName());
            stmt.setString(2, profile.getPhoneNumber());
            stmt.setString(3, profile.getAddress());
            stmt.setInt(4, profile.getUserID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update profile: " + e.getMessage());
        }

        return false;
    }

    public Profile findByUserId(int userID) {
        String sql = "SELECT * FROM profiles WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProfile(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari profile: " + e.getMessage());
        }

        return null;
    }

    public boolean deleteProfileByUserId(int userID) {
        String sql = "DELETE FROM profiles WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal delete profile: " + e.getMessage());
        }

        return false;
    }

    private Profile mapResultSetToProfile(ResultSet rs) throws SQLException {
        return new Profile(
                rs.getInt("profile_id"),
                rs.getInt("user_id"),
                rs.getString("full_name"),
                rs.getString("phone_number"),
                rs.getString("address")
        );
    }
}