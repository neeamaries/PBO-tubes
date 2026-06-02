package src.dao;

import src.config.DatabaseConnection;
import src.model.Category;
import src.model.Income;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAO {

    public boolean insertIncome(Income income) {
        String transactionSql = """
                INSERT INTO transactions
                (user_id, account_id, category_id, transaction_name, amount, transaction_type, transaction_date, note)
                VALUES (?, ?, ?, ?, ?, 'income', ?, ?)
                """;

        String incomeSql = "INSERT INTO income (transaction_id) VALUES (?)";
        String updateBalanceSql = "UPDATE account_wallets SET balance = balance + ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement transactionStmt = conn.prepareStatement(transactionSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement incomeStmt = conn.prepareStatement(incomeSql);
                 PreparedStatement balanceStmt = conn.prepareStatement(updateBalanceSql)) {

                transactionStmt.setInt(1, income.getUserID());
                transactionStmt.setInt(2, income.getAccountID());
                transactionStmt.setInt(3, income.getCategoryID());
                transactionStmt.setString(4, income.getTransactionName());
                transactionStmt.setDouble(5, income.getAmount());
                transactionStmt.setString(6, income.getTransactionDate());
                transactionStmt.setString(7, income.getNote());

                transactionStmt.executeUpdate();

                int transactionID;

                try (ResultSet rs = transactionStmt.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }
                    transactionID = rs.getInt(1);
                    income.setTransactionID(transactionID);
                }

                incomeStmt.setInt(1, transactionID);
                incomeStmt.executeUpdate();

                balanceStmt.setDouble(1, income.getAmount());
                balanceStmt.setInt(2, income.getAccountID());
                balanceStmt.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal insert income: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi insert income: " + e.getMessage());
        }

        return false;
    }

    public boolean updateIncome(Income income) {
        String oldSql = "SELECT amount, account_id FROM transactions WHERE transaction_id = ?";
        String updateSql = """
                UPDATE transactions
                SET account_id = ?, category_id = ?, transaction_name = ?, amount = ?,
                    transaction_date = ?, note = ?
                WHERE transaction_id = ? AND transaction_type = 'income'
                """;
        String rollbackBalanceSql = "UPDATE account_wallets SET balance = balance - ? WHERE account_id = ?";
        String applyBalanceSql = "UPDATE account_wallets SET balance = balance + ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                double oldAmount;
                int oldAccountID;

                try (PreparedStatement oldStmt = conn.prepareStatement(oldSql)) {
                    oldStmt.setInt(1, income.getTransactionID());

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
                    updateStmt.setInt(1, income.getAccountID());
                    updateStmt.setInt(2, income.getCategoryID());
                    updateStmt.setString(3, income.getTransactionName());
                    updateStmt.setDouble(4, income.getAmount());
                    updateStmt.setString(5, income.getTransactionDate());
                    updateStmt.setString(6, income.getNote());
                    updateStmt.setInt(7, income.getTransactionID());

                    if (updateStmt.executeUpdate() == 0) {
                        conn.rollback();
                        return false;
                    }
                }

                try (PreparedStatement applyStmt = conn.prepareStatement(applyBalanceSql)) {
                    applyStmt.setDouble(1, income.getAmount());
                    applyStmt.setInt(2, income.getAccountID());
                    applyStmt.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal update income: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi update income: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteIncome(int transactionID) {
        String oldSql = "SELECT amount, account_id FROM transactions WHERE transaction_id = ? AND transaction_type = 'income'";
        String deleteSql = "DELETE FROM transactions WHERE transaction_id = ?";
        String balanceSql = "UPDATE account_wallets SET balance = balance - ? WHERE account_id = ?";

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
                System.out.println("Gagal delete income: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi delete income: " + e.getMessage());
        }

        return false;
    }

    public Income findByTransactionId(int transactionID) {
        String sql = """
                SELECT i.income_id, t.*, c.name AS category_name, c.type AS category_type
                FROM income i
                JOIN transactions t ON i.transaction_id = t.transaction_id
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.transaction_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToIncome(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari income: " + e.getMessage());
        }

        return null;
    }

    public List<Income> findByUserId(int userID) {
        List<Income> incomes = new ArrayList<>();

        String sql = """
                SELECT i.income_id, t.*, c.name AS category_name, c.type AS category_type
                FROM income i
                JOIN transactions t ON i.transaction_id = t.transaction_id
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.user_id = ?
                ORDER BY t.transaction_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    incomes.add(mapResultSetToIncome(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil income user: " + e.getMessage());
        }

        return incomes;
    }

    private Income mapResultSetToIncome(ResultSet rs) throws SQLException {
        Category category = new Category(
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("category_type")
        );

        return new Income(
                rs.getInt("income_id"),
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