package src.dao;

import src.config.DatabaseConnection;
import src.model.Category;
import src.model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    public boolean insertExpense(Expense expense) {
        String transactionSql = """
                INSERT INTO transactions
                (user_id, account_id, category_id, transaction_name, amount, transaction_type, transaction_date, note)
                VALUES (?, ?, ?, ?, ?, 'expense', ?, ?)
                """;

        String expenseSql = "INSERT INTO expense (transaction_id) VALUES (?)";
        String updateBalanceSql = "UPDATE account_wallets SET balance = balance - ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement transactionStmt = conn.prepareStatement(transactionSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement expenseStmt = conn.prepareStatement(expenseSql);
                 PreparedStatement balanceStmt = conn.prepareStatement(updateBalanceSql)) {

                transactionStmt.setInt(1, expense.getUserID());
                transactionStmt.setInt(2, expense.getAccountID());
                transactionStmt.setInt(3, expense.getCategoryID());
                transactionStmt.setString(4, expense.getTransactionName());
                transactionStmt.setDouble(5, expense.getAmount());
                transactionStmt.setString(6, expense.getTransactionDate());
                transactionStmt.setString(7, expense.getNote());

                transactionStmt.executeUpdate();

                int transactionID;

                try (ResultSet rs = transactionStmt.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }

                    transactionID = rs.getInt(1);
                    expense.setTransactionID(transactionID);
                }

                expenseStmt.setInt(1, transactionID);
                expenseStmt.executeUpdate();

                balanceStmt.setDouble(1, expense.getAmount());
                balanceStmt.setInt(2, expense.getAccountID());
                balanceStmt.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal insert expense: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi insert expense: " + e.getMessage());
        }

        return false;
    }

    public boolean updateExpense(Expense expense) {
        String oldSql = "SELECT amount, account_id FROM transactions WHERE transaction_id = ?";
        String updateSql = """
                UPDATE transactions
                SET account_id = ?, category_id = ?, transaction_name = ?, amount = ?,
                    transaction_date = ?, note = ?
                WHERE transaction_id = ? AND transaction_type = 'expense'
                """;
        String rollbackBalanceSql = "UPDATE account_wallets SET balance = balance + ? WHERE account_id = ?";
        String applyBalanceSql = "UPDATE account_wallets SET balance = balance - ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                double oldAmount;
                int oldAccountID;

                try (PreparedStatement oldStmt = conn.prepareStatement(oldSql)) {
                    oldStmt.setInt(1, expense.getTransactionID());

                    try (ResultSet rs = oldStmt.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        oldAmount = rs.getDouble("amount");
                        oldAccountID = rs.getInt("account_id");
                    }
                }

                try (PreparedStatement rollbackStmt = conn.prepareStatement(rollbackBalanceSql)) {
                    rollbackStmt.setDouble(1, oldAmount);
                    rollbackStmt.setInt(2, oldAccountID);
                    rollbackStmt.executeUpdate();
                }

                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, expense.getAccountID());
                    updateStmt.setInt(2, expense.getCategoryID());
                    updateStmt.setString(3, expense.getTransactionName());
                    updateStmt.setDouble(4, expense.getAmount());
                    updateStmt.setString(5, expense.getTransactionDate());
                    updateStmt.setString(6, expense.getNote());
                    updateStmt.setInt(7, expense.getTransactionID());

                    if (updateStmt.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement applyStmt = conn.prepareStatement(applyBalanceSql)) {
                    applyStmt.setDouble(1, expense.getAmount());
                    applyStmt.setInt(2, expense.getAccountID());
                    applyStmt.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal update expense: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi update expense: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteExpense(int transactionID) {
        String oldSql = "SELECT amount, account_id FROM transactions WHERE transaction_id = ? AND transaction_type = 'expense'";
        String deleteSql = "DELETE FROM transactions WHERE transaction_id = ?";
        String balanceSql = "UPDATE account_wallets SET balance = balance + ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                double amount;
                int accountID;

                try (PreparedStatement oldStmt = conn.prepareStatement(oldSql)) {
                    oldStmt.setInt(1, transactionID);

                    try (ResultSet rs = oldStmt.executeQuery()) {
                        if (!rs.next()) {
                            conn.rollback();
                            return false;
                        }

                        amount = rs.getDouble("amount");
                        accountID = rs.getInt("account_id");
                    }
                }

                try (PreparedStatement balanceStmt = conn.prepareStatement(balanceSql)) {
                    balanceStmt.setDouble(1, amount);
                    balanceStmt.setInt(2, accountID);
                    balanceStmt.executeUpdate();
                }

                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                    deleteStmt.setInt(1, transactionID);
                    deleteStmt.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal delete expense: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi delete expense: " + e.getMessage());
        }

        return false;
    }

    public Expense findByTransactionId(int transactionID) {
        String sql = """
                SELECT e.expense_id, t.*, c.name AS category_name, c.type AS category_type
                FROM expense e
                JOIN transactions t ON e.transaction_id = t.transaction_id
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.transaction_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExpense(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari expense: " + e.getMessage());
        }

        return null;
    }

    public List<Expense> findByUserId(int userID) {
        List<Expense> expenses = new ArrayList<>();

        String sql = """
                SELECT e.expense_id, t.*, c.name AS category_name, c.type AS category_type
                FROM expense e
                JOIN transactions t ON e.transaction_id = t.transaction_id
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.user_id = ?
                ORDER BY t.transaction_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    expenses.add(mapResultSetToExpense(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil expense user: " + e.getMessage());
        }

        return expenses;
    }

    private Expense mapResultSetToExpense(ResultSet rs) throws SQLException {
        Category category = new Category(
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("category_type")
        );

        return new Expense(
                rs.getInt("expense_id"),
                rs.getInt("transaction_id"),
                rs.getInt("user_id"),
                rs.getInt("account_id"),
                rs.getInt("category_id"),
                rs.getString("transaction_name"),
                rs.getDouble("amount"),
                rs.getString("transaction_date"),
                rs.getString("note"),
                category
        );
    }
}