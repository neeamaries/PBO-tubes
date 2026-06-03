package src.dao;

import src.config.DatabaseConnection;
import src.model.Category;
import src.model.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public int insertTransaction(Transaction transaction) {
        String sql = """
                INSERT INTO transactions
                (user_id, account_id, category_id, transaction_name, amount, transaction_type, transaction_date, note)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            fillTransactionStatement(stmt, transaction);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int transactionID = rs.getInt(1);
                        transaction.setTransactionID(transactionID);
                        return transactionID;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert transaction: " + e.getMessage());
        }

        return -1;
    }

    public boolean updateTransaction(Transaction transaction) {
        String sql = """
                UPDATE transactions
                SET user_id = ?, account_id = ?, category_id = ?, transaction_name = ?,
                    amount = ?, transaction_type = ?, transaction_date = ?, note = ?
                WHERE transaction_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            fillTransactionStatement(stmt, transaction);
            stmt.setInt(9, transaction.getTransactionID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update transaction: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteTransaction(int transactionID) {
        String sql = "DELETE FROM transactions WHERE transaction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal delete transaction: " + e.getMessage());
        }

        return false;
    }

    public Transaction findById(int transactionID) {
        String sql = """
                SELECT t.*, c.name AS category_name, c.type AS category_type
                FROM transactions t
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.transaction_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transactionID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari transaction: " + e.getMessage());
        }

        return null;
    }

    public List<Transaction> findByUserId(int userID) {
        List<Transaction> transactions = new ArrayList<>();

        String sql = """
                SELECT t.*, c.name AS category_name, c.type AS category_type
                FROM transactions t
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.user_id = ?
                ORDER BY t.transaction_date DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil transaction user: " + e.getMessage());
        }

        return transactions;
    }

    public List<Transaction> findByFilter(int userID, Integer accountID, Integer categoryID,
                                          String type, String startDate, String endDate) {
        List<Transaction> transactions = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
                SELECT t.*, c.name AS category_name, c.type AS category_type
                FROM transactions t
                JOIN categories c ON t.category_id = c.category_id
                WHERE t.user_id = ?
                """);

        List<Object> params = new ArrayList<>();
        params.add(userID);

        if (accountID != null) {
            sql.append(" AND t.account_id = ?");
            params.add(accountID);
        }

        if (categoryID != null) {
            sql.append(" AND t.category_id = ?");
            params.add(categoryID);
        }

        if (type != null && !type.isBlank()) {
            sql.append(" AND t.transaction_type = ?");
            params.add(type.toLowerCase());
        }
        if (startDate != null && endDate != null) {
            sql.append(" AND CAST(t.transaction_date AS DATE) BETWEEN ? AND ?");
            params.add(startDate);
            params.add(endDate);
        }

        sql.append(" ORDER BY t.transaction_date DESC");

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal filter transaction: " + e.getMessage());
        }

        return transactions;
    }

    private void fillTransactionStatement(PreparedStatement stmt, Transaction transaction) throws SQLException {
        stmt.setInt(1, transaction.getUserID());
        stmt.setInt(2, transaction.getAccountID());
        stmt.setInt(3, transaction.getCategoryID());
        stmt.setString(4, transaction.getTransactionName());
        stmt.setDouble(5, transaction.getAmount());
        stmt.setString(6, transaction.getTransactionType().toLowerCase());
        stmt.setString(7, transaction.getTransactionDate());
        stmt.setString(8, transaction.getNote());
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Category category = new Category(
                rs.getInt("category_id"),
                rs.getString("category_name"),
                rs.getString("category_type")
        );

        return new Transaction(
                rs.getInt("transaction_id"),
                rs.getInt("user_id"),
                rs.getInt("account_id"),
                rs.getInt("category_id"),
                rs.getString("transaction_name"),
                rs.getDouble("amount"),
                rs.getString("transaction_type"),
                rs.getString("transaction_date"),
                rs.getString("note"),
                category
        );
    }
}