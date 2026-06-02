package src.dao;

import src.config.DatabaseConnection;
import src.model.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public Report generateReport(int userID, int accountID, String reportType, String startDate, String endDate) {
        String totalSql = """
                SELECT
                    COALESCE(SUM(CASE WHEN transaction_type = 'income' THEN amount ELSE 0 END), 0) AS total_income,
                    COALESCE(SUM(CASE WHEN transaction_type = 'expense' THEN amount ELSE 0 END), 0) AS total_expense
                FROM transactions
                WHERE user_id = ?
                AND account_id = ?
                AND CAST(transaction_date AS DATE) BETWEEN ? AND ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(totalSql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, accountID);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double totalIncome = rs.getDouble("total_income");
                    double totalExpense = rs.getDouble("total_expense");
                    double endingBalance = totalIncome - totalExpense;

                    Report report = new Report(
                            0,
                            userID,
                            accountID,
                            reportType.toLowerCase(),
                            startDate,
                            endDate,
                            totalIncome,
                            totalExpense,
                            endingBalance,
                            null
                    );

                    insertReport(report);
                    return report;
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal generate report: " + e.getMessage());
        }

        return null;
    }

    public boolean insertReport(Report report) {
        String sql = """
                INSERT INTO reports
                (user_id, account_id, report_type, start_date, end_date, total_income, total_expense, ending_balance)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, report.getUserID());
            stmt.setInt(2, report.getAccountID());
            stmt.setString(3, report.getReportType().toLowerCase());
            stmt.setString(4, report.getStartDate());
            stmt.setString(5, report.getEndDate());
            stmt.setDouble(6, report.getTotalIncome());
            stmt.setDouble(7, report.getTotalExpense());
            stmt.setDouble(8, report.getEndingBalance());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        report.setReportID(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert report: " + e.getMessage());
        }

        return false;
    }

    public Report findById(int reportID) {
        String sql = "SELECT * FROM reports WHERE report_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reportID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToReport(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari report: " + e.getMessage());
        }

        return null;
    }

    public List<Report> findByUserId(int userID) {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports WHERE user_id = ? ORDER BY generated_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToReport(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil report user: " + e.getMessage());
        }

        return reports;
    }

    public List<Report> findByAccountId(int accountID) {
        List<Report> reports = new ArrayList<>();
        String sql = "SELECT * FROM reports WHERE account_id = ? ORDER BY generated_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reports.add(mapResultSetToReport(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil report account: " + e.getMessage());
        }

        return reports;
    }

    private Report mapResultSetToReport(ResultSet rs) throws SQLException {
        return new Report(
                rs.getInt("report_id"),
                rs.getInt("user_id"),
                rs.getInt("account_id"),
                rs.getString("report_type"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getDouble("total_income"),
                rs.getDouble("total_expense"),
                rs.getDouble("ending_balance"),
                rs.getString("generated_at")
        );
    }
}