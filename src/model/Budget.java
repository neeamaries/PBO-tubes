package src.model;

public class Budget {
    private double totalBudget;
    private double categoryBudget;
    private double threshold;

    public Budget() {
    }

    public Budget(double totalBudget, double categoryBudget, double threshold) {
        this.totalBudget = totalBudget;
        this.categoryBudget = categoryBudget;
        this.threshold = threshold;
    }

    public void setBudget(double totalBudget, double categoryBudget, double threshold) {
        this.totalBudget = totalBudget;
        this.categoryBudget = categoryBudget;
        this.threshold = threshold;
    }

    public boolean checkThreshold(double totalExpense) {
        double batasPeringatan = totalBudget * threshold;

        return totalExpense >= batasPeringatan;
    }

    public boolean checkCategoryThreshold(double expenseCategoryTotal) {
        double batasPeringatan = categoryBudget * threshold;

        return expenseCategoryTotal >= batasPeringatan;
    }

    public void showBudgetInfo() {
        System.out.println("=== DETAIL BUDGET ===");
        System.out.println("Total Budget    : " + totalBudget);
        System.out.println("Category Budget : " + categoryBudget);
        System.out.println("Threshold       : " + threshold);
        System.out.println("Batas Peringatan Total    : " + (totalBudget * threshold));
        System.out.println("Batas Peringatan Kategori : " + (categoryBudget * threshold));
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getCategoryBudget() {
        return categoryBudget;
    }

    public void setCategoryBudget(double categoryBudget) {
        this.categoryBudget = categoryBudget;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "totalBudget=" + totalBudget +
                ", categoryBudget=" + categoryBudget +
                ", threshold=" + threshold +
                '}';
    }
}
