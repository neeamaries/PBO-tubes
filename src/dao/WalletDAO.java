package src.dao;

import src.config.DatabaseConnection;
import src.model.AccountWallet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WalletDAO {

    public int insertWallet(AccountWallet wallet) {
        String sql = """
                INSERT INTO account_wallets (user_id, account_name, balance)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, wallet.getUserID());
            stmt.setString(2, wallet.getAccountName());
            stmt.setDouble(3, wallet.getBalance());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int accountID = rs.getInt(1);
                        wallet.setAccountID(accountID);
                        return accountID;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert wallet: " + e.getMessage());
        }

        return -1;
    }

    public boolean insertPhysicalWallet(AccountWallet wallet) {
        String walletSql = """
                INSERT INTO account_wallets (user_id, account_name, balance)
                VALUES (?, ?, ?)
                """;

        String physicalSql = "INSERT INTO physical_wallet (account_id) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement walletStmt = conn.prepareStatement(walletSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement physicalStmt = conn.prepareStatement(physicalSql)) {

                walletStmt.setInt(1, wallet.getUserID());
                walletStmt.setString(2, wallet.getAccountName());
                walletStmt.setDouble(3, wallet.getBalance());

                int rows = walletStmt.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    return false;
                }

                int accountID;

                try (ResultSet rs = walletStmt.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }

                    accountID = rs.getInt(1);
                    wallet.setAccountID(accountID);
                }

                physicalStmt.setInt(1, accountID);
                physicalStmt.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal insert physical wallet: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi insert physical wallet: " + e.getMessage());
        }

        return false;
    }

    public boolean insertEWallet(AccountWallet wallet, String providerName, String accountNumber) {
        String walletSql = """
                INSERT INTO account_wallets (user_id, account_name, balance)
                VALUES (?, ?, ?)
                """;

        String ewalletSql = """
                INSERT INTO ewallet (account_id, provider_name, account_number)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement walletStmt = conn.prepareStatement(walletSql, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ewalletStmt = conn.prepareStatement(ewalletSql)) {

                walletStmt.setInt(1, wallet.getUserID());
                walletStmt.setString(2, wallet.getAccountName());
                walletStmt.setDouble(3, wallet.getBalance());

                int rows = walletStmt.executeUpdate();

                if (rows == 0) {
                    conn.rollback();
                    return false;
                }

                int accountID;

                try (ResultSet rs = walletStmt.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return false;
                    }

                    accountID = rs.getInt(1);
                    wallet.setAccountID(accountID);
                }

                ewalletStmt.setInt(1, accountID);
                ewalletStmt.setString(2, providerName);
                ewalletStmt.setString(3, accountNumber);
                ewalletStmt.executeUpdate();

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Gagal insert ewallet: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println("Gagal koneksi insert ewallet: " + e.getMessage());
        }

        return false;
    }

    public boolean updateWallet(AccountWallet wallet) {
        String sql = """
                UPDATE account_wallets
                SET account_name = ?, balance = ?
                WHERE account_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, wallet.getAccountName());
            stmt.setDouble(2, wallet.getBalance());
            stmt.setInt(3, wallet.getAccountID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update wallet: " + e.getMessage());
        }

        return false;
    }

    public boolean updateEWallet(int accountID, String providerName, String accountNumber) {
        String sql = """
                UPDATE ewallet
                SET provider_name = ?, account_number = ?
                WHERE account_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, providerName);
            stmt.setString(2, accountNumber);
            stmt.setInt(3, accountID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update ewallet: " + e.getMessage());
        }

        return false;
    }

    public boolean deleteWallet(int accountID) {
        String sql = "DELETE FROM account_wallets WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal delete wallet: " + e.getMessage());
        }

        return false;
    }

    public AccountWallet findById(int accountID) {
        String sql = "SELECT * FROM account_wallets WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToWallet(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal cari wallet: " + e.getMessage());
        }

        return null;
    }

    public List<AccountWallet> findByUserId(int userID) {
        List<AccountWallet> wallets = new ArrayList<>();
        String sql = "SELECT * FROM account_wallets WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    wallets.add(mapResultSetToWallet(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal ambil wallet user: " + e.getMessage());
        }

        return wallets;
    }

    public double getTotalBalance(int userID) {
        String sql = """
                SELECT COALESCE(SUM(balance), 0) AS total_balance
                FROM account_wallets
                WHERE user_id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("total_balance");
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal hitung total balance: " + e.getMessage());
        }

        return 0;
    }

    public boolean updateBalance(int accountID, double newBalance) {
        String sql = "UPDATE account_wallets SET balance = ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, newBalance);
            stmt.setInt(2, accountID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update balance: " + e.getMessage());
        }

        return false;
    }

    public boolean addBalance(int accountID, double amount) {
        String sql = "UPDATE account_wallets SET balance = balance + ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, accountID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal tambah balance: " + e.getMessage());
        }

        return false;
    }

    public boolean subtractBalance(int accountID, double amount) {
        String sql = "UPDATE account_wallets SET balance = balance - ? WHERE account_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setInt(2, accountID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal kurang balance: " + e.getMessage());
        }

        return false;
    }

    private AccountWallet mapResultSetToWallet(ResultSet rs) throws SQLException {
        return new AccountWallet(
                rs.getInt("account_id"),
                rs.getInt("user_id"),
                rs.getString("account_name"),
                rs.getDouble("balance")
        );
    }
}