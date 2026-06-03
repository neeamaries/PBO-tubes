package src.dao;

import src.config.DatabaseConnection;
import src.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // INSERT category, return category_id baru
    // Kalau gagal, return -1
    public int insertCategory(Category category) {
        String sql = "INSERT INTO categories (name, type) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType().toLowerCase());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int categoryID = rs.getInt(1);
                        category.setCategoryID(categoryID);
                        return categoryID;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal insert category.");
            e.printStackTrace();
        }

        return -1;
    }

    // Cari category berdasarkan ID
    public Category findById(int categoryID) {
        String sql = "SELECT category_id, name, type FROM categories WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mencari category.");
            e.printStackTrace();
        }

        return null;
    }

    // Ambil semua category
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_id, name, type FROM categories";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil categories.");
            e.printStackTrace();
        }

        return categories;
    }

    // Alias biar konsisten dengan DAO lain
    public List<Category> findAll() {
        return getAllCategories();
    }

    // Ambil category berdasarkan type: income / expense
    public List<Category> findByType(String type) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT category_id, name, type FROM categories WHERE type = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type.toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil categories by type.");
            e.printStackTrace();
        }

        return categories;
    }

    // Update nama dan type category
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, type = ? WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getType().toLowerCase());
            stmt.setInt(3, category.getCategoryID());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update category.");
            e.printStackTrace();
        }

        return false;
    }

    // Update nama category saja
    public boolean updateCategoryName(int categoryID, String newName) {
        String sql = "UPDATE categories SET name = ? WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newName);
            stmt.setInt(2, categoryID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update category name.");
            e.printStackTrace();
        }

        return false;
    }

    // Update type category saja
    public boolean updateCategoryType(int categoryID, String newType) {
        String sql = "UPDATE categories SET type = ? WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newType.toLowerCase());
            stmt.setInt(2, categoryID);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal update category type.");
            e.printStackTrace();
        }

        return false;
    }

    // Delete category
    public boolean deleteCategory(int categoryID) {
        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Gagal delete category.");
            e.printStackTrace();
        }

        return false;
    }

    // Mapping ResultSet ke object Category
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getInt("category_id"),
                rs.getString("name"),
                rs.getString("type")
        );
    }
}