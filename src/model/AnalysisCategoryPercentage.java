package src.model;

public class AnalysisCategoryPercentage {
    private int id;
    private int analysisID;
    private int categoryID;
    private double percentage;
    private double totalAmount;

    public AnalysisCategoryPercentage() {
    }

    public AnalysisCategoryPercentage(int id, int analysisID, int categoryID, double percentage, double totalAmount) {
        this.id = id;
        this.analysisID = analysisID;
        this.categoryID = categoryID;
        this.percentage = percentage;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public int getAnalysisID() {
        return analysisID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnalysisID(int analysisID) {
        this.analysisID = analysisID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "AnalysisCategoryPercentage{" +
                "id=" + id +
                ", analysisID=" + analysisID +
                ", categoryID=" + categoryID +
                ", percentage=" + percentage +
                ", totalAmount=" + totalAmount +
                '}';
    }
}