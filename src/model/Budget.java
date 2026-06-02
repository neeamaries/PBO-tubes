package src.model;

public class Budget {
    private int budgetID;
    private int userID;
    private Integer categoryID;
    private double totalBudget;
    private Double categoryBudget;
    private double threshold;
    private String startDate;
    private String endDate;

    public Budget() {
    }

    public Budget(int budgetID, int userID, Integer categoryID, double totalBudget, Double categoryBudget,
                  double threshold, String startDate, String endDate) {
        this.budgetID = budgetID;
        this.userID = userID;
        this.categoryID = categoryID;
        this.totalBudget = totalBudget;
        this.categoryBudget = categoryBudget;
        this.threshold = threshold;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean checkThreshold(double totalExpense) {
        double batasPeringatan = totalBudget * threshold;
        return totalExpense >= batasPeringatan;
    }

    public boolean checkCategoryThreshold(double expenseCategoryTotal) {
        if (categoryBudget == null) {
            return false;
        }

        double batasPeringatan = categoryBudget * threshold;
        return expenseCategoryTotal >= batasPeringatan;
    }

    public int getBudgetID() {
        return budgetID;
    }

    public void setBudgetID(int budgetID) {
        this.budgetID = budgetID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public Double getCategoryBudget() {
        return categoryBudget;
    }

    public void setCategoryBudget(Double categoryBudget) {
        this.categoryBudget = categoryBudget;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetID=" + budgetID +
                ", userID=" + userID +
                ", categoryID=" + categoryID +
                ", totalBudget=" + totalBudget +
                ", categoryBudget=" + categoryBudget +
                ", threshold=" + threshold +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}