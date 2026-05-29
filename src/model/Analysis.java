package src.model;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analysis {
    private double totalExpense;
    private List<Expense> expenses;

    public Analysis() {
    }

    public Analysis(List<Expense> expenses) {
        this.expenses = expenses;
        this.totalExpense = calculateTotalExpense();
    }

    private double calculateTotalExpense() {
        double total = 0;

        if (expenses == null) {
            return total;
        }

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        return total;
    }

    public String largestCategory() {
        if (expenses == null || expenses.isEmpty()) {
            return "Belum ada pengeluaran.";
        }

        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            String categoryName = expense.getCategoryName();

            double currentTotal = categoryTotals.getOrDefault(categoryName, 0.0);
            categoryTotals.put(categoryName, currentTotal + expense.getAmount());
        }

        String largestCategory = "";
        double largestAmount = 0;

        for (String category : categoryTotals.keySet()) {
            double amount = categoryTotals.get(category);

            if (amount > largestAmount) {
                largestAmount = amount;
                largestCategory = category;
            }
        }

        return largestCategory;
    }

    public double averageExpend() {
        if (expenses == null || expenses.isEmpty()) {
            return 0;
        }

        return calculateTotalExpense() / expenses.size();
    }

    public double categoryPercentage(String categoryName) {
        double total = calculateTotalExpense();

        if (total == 0) {
            return 0;
        }

        double categoryTotal = 0;

        for (Expense expense : expenses) {
            if (expense.getCategoryName().equalsIgnoreCase(categoryName)) {
                categoryTotal += expense.getAmount();
            }
        }

        return (categoryTotal / total) * 100;
    }

    public double getTotalExpense() {
        this.totalExpense = calculateTotalExpense();
        return totalExpense;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
        this.totalExpense = calculateTotalExpense();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
    //tambahan
    public double totalByCategory(String categoryName) {
    double categoryTotal = 0;

    if (expenses == null) {
        return 0;
    }

    for (Expense expense : expenses) {
        if (expense.getCategoryName().equalsIgnoreCase(categoryName)) {
            categoryTotal += expense.getAmount();
        }
    }

    return categoryTotal;
}
}
