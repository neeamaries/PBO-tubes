package src.dao;

import src.config.DatabaseConnection;
import src.model.Budget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

    public boolean insertBudget(Budget budget) {
        String sql = """
                INSERT INTO budgets
                (user_id, category_id, total_budget, category_budget, threshold, start_date, end_date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            fillBudgetStatement(stmt, budget);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        budget.setBudgetID(rs.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert budget: " + e.getMessage());
        }

        return false;
    }

    public boolean updateBudget(Budget budget) {
        String sql = """
                UPDATE budgets
                SET user_id = ?, category_id = ?, total_budget = ?, category_budget = ?,
                    threshold = ?, start_date = ?, end_date = ?
                WHERE budget_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            fillBudgetStatement(stmt, budget);
            stmt.setInt(8, budget.getBudgetID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update budget: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteBudget(int budgetID) {
        String sql = "DELETE FROM budgets WHERE budget_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, budgetID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal delete budget: " + e.getMessage());
        }

        return false;
    }

    public Budget findById(int budgetID) {
        String sql = "SELECT * FROM budgets WHERE budget_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, budgetID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToBudget(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari budget: " + e.getMessage());
        }

        return null;
    }

    public List<Budget> findByUserId(int userID) {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    budgets.add(mapResultSetToBudget(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil budget user: " + e.getMessage());
        }

        return budgets;
    }

    public List<Budget> findActiveBudgets(int userID, String currentDate) {
        List<Budget> budgets = new ArrayList<>();

        String sql = """
                SELECT * FROM budgets
                WHERE user_id = ?
                AND ? BETWEEN start_date AND end_date
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setString(2, currentDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    budgets.add(mapResultSetToBudget(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil active budget: " + e.getMessage());
        }

        return budgets;
    }

    public double getTotalExpenseInPeriod(int userID, String startDate, String endDate) {
        String sql = """
                SELECT COALESCE(SUM(amount), 0) AS total_expense
                FROM transactions
                WHERE user_id = ?
                AND transaction_type = 'expense'
                AND CAST(transaction_date AS DATE) BETWEEN ? AND ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setString(2, startDate);
            stmt.setString(3, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_expense");
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal hitung total expense: " + e.getMessage());
        }

        return 0;
    }

    public double getCategoryExpenseInPeriod(int userID, int categoryID, String startDate, String endDate) {
        String sql = """
                SELECT COALESCE(SUM(amount), 0) AS category_expense
                FROM transactions
                WHERE user_id = ?
                AND category_id = ?
                AND transaction_type = 'expense'
                AND CAST(transaction_date AS DATE) BETWEEN ? AND ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, categoryID);
            stmt.setString(3, startDate);
            stmt.setString(4, endDate);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("category_expense");
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal hitung category expense: " + e.getMessage());
        }

        return 0;
    }

    public boolean isTotalBudgetExceeded(Budget budget) {
        double totalExpense = getTotalExpenseInPeriod(
                budget.getUserID(),
                budget.getStartDate(),
                budget.getEndDate()
        );

        return totalExpense >= budget.getTotalBudget() * budget.getThreshold();
    }

    public boolean isCategoryBudgetExceeded(Budget budget) {
        if (budget.getCategoryID() == null || budget.getCategoryBudget() == null) {
            return false;
        }

        double categoryExpense = getCategoryExpenseInPeriod(
                budget.getUserID(),
                budget.getCategoryID(),
                budget.getStartDate(),
                budget.getEndDate()
        );

        return categoryExpense >= budget.getCategoryBudget() * budget.getThreshold();
    }

    private void fillBudgetStatement(PreparedStatement stmt, Budget budget) throws SQLException {
        stmt.setInt(1, budget.getUserID());

        if (budget.getCategoryID() == null) {
            stmt.setNull(2, Types.INTEGER);
        } else {
            stmt.setInt(2, budget.getCategoryID());
        }

        stmt.setDouble(3, budget.getTotalBudget());

        if (budget.getCategoryBudget() == null) {
            stmt.setNull(4, Types.DOUBLE);
        } else {
            stmt.setDouble(4, budget.getCategoryBudget());
        }

        stmt.setDouble(5, budget.getThreshold());
        stmt.setString(6, budget.getStartDate());
        stmt.setString(7, budget.getEndDate());
    }

    private Budget mapResultSetToBudget(ResultSet rs) throws SQLException {
        Integer categoryID = rs.getObject("category_id") == null ? null : rs.getInt("category_id");
        Double categoryBudget = rs.getObject("category_budget") == null ? null : rs.getDouble("category_budget");

        return new Budget(
                rs.getInt("budget_id"),
                rs.getInt("user_id"),
                categoryID,
                rs.getDouble("total_budget"),
                categoryBudget,
                rs.getDouble("threshold"),
                rs.getString("start_date"),
                rs.getString("end_date")
        );
    }
}
