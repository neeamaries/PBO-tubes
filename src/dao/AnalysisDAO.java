package src.dao;

import src.config.DatabaseConnection;
import src.model.Analysis;
import src.model.AnalysisCategoryPercentage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnalysisDAO {

    public Analysis generateAnalysis(int userID, String startDate, String endDate) {
        String summarySql = """
                SELECT
                    COALESCE(SUM(amount), 0) AS total_expense,
                    COALESCE(AVG(amount), 0) AS average_expense
                FROM transactions
                WHERE user_id = ?
                AND transaction_type = 'expense'
                AND CAST(transaction_date AS DATE) BETWEEN ? AND ?
                """;

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                double totalExpense = 0;
                double averageExpense = 0;
                Integer largestCategoryID = findLargestExpenseCategory(conn, userID, startDate, endDate);

                try (PreparedStatement summaryStmt = conn.prepareStatement(summarySql)) {
                    summaryStmt.setInt(1, userID);
                    summaryStmt.setString(2, startDate);
                    summaryStmt.setString(3, endDate);

                    try (ResultSet rs = summaryStmt.executeQuery()) {
                        if (rs.next()) {
                            totalExpense = rs.getDouble("total_expense");
                            averageExpense = rs.getDouble("average_expense");
                        }
                    }
                }

                Analysis analysis = new Analysis(
                        0,
                        userID,
                        startDate,
                        endDate,
                        totalExpense,
                        largestCategoryID,
                        averageExpense,
                        null
                );

                int analysisID = insertAnalysis(conn, analysis);

                if (analysisID == -1) {
                    conn.rollback();
                    return null;
                }

                analysis.setAnalysisID(analysisID);

                List<AnalysisCategoryPercentage> percentages =
                        calculateCategoryPercentages(conn, analysisID, userID, startDate, endDate, totalExpense);

                for (AnalysisCategoryPercentage percentage : percentages) {
                    insertCategoryPercentage(conn, percentage);
                }

                conn.commit();
                return analysis;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal generate analysis: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi analysis: " + e.getMessage());
        }

        return null;
    }

    public boolean insertAnalysis(Analysis analysis) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            int id = insertAnalysis(conn, analysis);
            if (id != -1) {
                analysis.setAnalysisID(id);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Gagal insert analysis: " + e.getMessage());
        }

        return false;
    }

    private int insertAnalysis(Connection conn, Analysis analysis) throws SQLException {
        String sql = """
                INSERT INTO analysis
                (user_id, start_date, end_date, total_expense, largest_category_id, average_expense)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, analysis.getUserID());
            stmt.setString(2, analysis.getStartDate());
            stmt.setString(3, analysis.getEndDate());
            stmt.setDouble(4, analysis.getTotalExpense());

            if (analysis.getLargestCategoryID() == null) {
                stmt.setNull(5, Types.INTEGER);
            } else {
                stmt.setInt(5, analysis.getLargestCategoryID());
            }

            stmt.setDouble(6, analysis.getAverageExpense());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        }

        return -1;
    }

    public boolean insertCategoryPercentage(AnalysisCategoryPercentage percentage) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            insertCategoryPercentage(conn, percentage);
            return true;
        } catch (SQLException e) {
            System.out.println("Gagal insert category percentage: " + e.getMessage());
        }

        return false;
    }

    private void insertCategoryPercentage(Connection conn, AnalysisCategoryPercentage percentage) throws SQLException {
        String sql = """
                INSERT INTO analysis_category_percentage
                (analysis_id, category_id, percentage, total_amount)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, percentage.getAnalysisID());
            stmt.setInt(2, percentage.getCategoryID());
            stmt.setDouble(3, percentage.getPercentage());
            stmt.setDouble(4, percentage.getTotalAmount());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    percentage.setId(rs.getInt(1));
                }
            }
        }
    }

    public Analysis findById(int analysisID) {
        String sql = "SELECT * FROM analysis WHERE analysis_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, analysisID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAnalysis(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari analysis: " + e.getMessage());
        }

        return null;
    }

    public List<Analysis> findByUserId(int userID) {
        List<Analysis> analyses = new ArrayList<>();

        String sql = """
                SELECT * FROM analysis
                WHERE user_id = ?
                ORDER BY generated_at DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    analyses.add(mapResultSetToAnalysis(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil analysis user: " + e.getMessage());
        }

        return analyses;
    }

    public List<AnalysisCategoryPercentage> findPercentagesByAnalysisId(int analysisID) {
        List<AnalysisCategoryPercentage> percentages = new ArrayList<>();

        String sql = """
                SELECT * FROM analysis_category_percentage
                WHERE analysis_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, analysisID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    percentages.add(mapResultSetToPercentage(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil percentage analysis: " + e.getMessage());
        }

        return percentages;
    }

    public Integer findLargestExpenseCategory(int userID, String startDate, String endDate) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return findLargestExpenseCategory(conn, userID, startDate, endDate);
        } catch (SQLException e) {
            System.out.println("Gagal cari kategori terbesar: " + e.getMessage());
        }

        return null;
    }

    private Integer findLargestExpenseCategory(Connection conn, int userID, String startDate, String endDate) throws SQLException {
        String sql = """
                SELECT category_id, SUM(amount) AS total_amount
                FROM transactions
                WHERE user_id = ?
                AND transaction_type = 'expense'
                AND CAST(transaction_date AS DATE) BETWEEN ? AND ?
                GROUP BY category_id
                ORDER BY total_amount DESC
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("category_id");
                }
            }
        }

        return null;
    }

    private List<AnalysisCategoryPercentage> calculateCategoryPercentages(Connection conn, int analysisID, int userID,
                                                                          String startDate, String endDate,
                                                                          double totalExpense) throws SQLException {
        List<AnalysisCategoryPercentage> result = new ArrayList<>();

        String sql = """
                SELECT category_id, SUM(amount) AS total_amount
                FROM transactions
                WHERE user_id = ?
                AND transaction_type = 'expense'
                AND CAST(transaction_date AS DATE) BETWEEN ? AND ?
                GROUP BY category_id
                """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userID);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int categoryID = rs.getInt("category_id");
                    double totalAmount = rs.getDouble("total_amount");
                    double percentage = totalExpense == 0 ? 0 : (totalAmount / totalExpense) * 100;

                    result.add(new AnalysisCategoryPercentage(
                            0,
                            analysisID,
                            categoryID,
                            percentage,
                            totalAmount
                    ));
                }
            }
        }

        return result;
    }

    private Analysis mapResultSetToAnalysis(ResultSet rs) throws SQLException {
        Integer largestCategoryID = rs.getObject("largest_category_id") == null
                ? null
                : rs.getInt("largest_category_id");

        return new Analysis(
                rs.getInt("analysis_id"),
                rs.getInt("user_id"),
                rs.getString("start_date"),
                rs.getString("end_date"),
                rs.getDouble("total_expense"),
                largestCategoryID,
                rs.getDouble("average_expense"),
                rs.getString("generated_at")
        );
    }

    private AnalysisCategoryPercentage mapResultSetToPercentage(ResultSet rs) throws SQLException {
        return new AnalysisCategoryPercentage(
                rs.getInt("id"),
                rs.getInt("analysis_id"),
                rs.getInt("category_id"),
                rs.getDouble("percentage"),
                rs.getDouble("total_amount")
        );
    }
}