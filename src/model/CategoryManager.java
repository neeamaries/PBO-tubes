package src.model;
import java.util.ArrayList;
import java.util.List;
public class CategoryManager {
    private List<Category> categories;

    public CategoryManager() {
        this.categories = new ArrayList<>();
    }

    public void addCategory(String name, String type) {
        int newID = categories.size() + 1;
        Category category = new Category(newID, name, type);
        categories.add(category);
    }

    public boolean editCategory(int id, String newName) {
        for (Category category : categories) {
            if (category.getCategoryID() == id) {
                category.setName(newName);
                return true;
            }
        }

        return false;
    }

    public boolean deleteCategory(int id) {
        Category categoryYangDihapus = null;

        for (Category category : categories) {
            if (category.getCategoryID() == id) {
                categoryYangDihapus = category;
                break;
            }
        }

        if (categoryYangDihapus != null) {
            categories.remove(categoryYangDihapus);
            return true;
        }

        return false;
    }

    public List<Category> getCategoriesByType(String type) {
        List<Category> result = new ArrayList<>();

        for (Category category : categories) {
            if (category.getType().equalsIgnoreCase(type)) {
                result.add(category);
            }
        }

        return result;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void showAllCategories() {
        if (categories.isEmpty()) {
            System.out.println("Belum ada kategori.");
            return;
        }

        for (Category category : categories) {
            System.out.println(category.getCategoryInfo());
        }
    }
}
