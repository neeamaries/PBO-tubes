package src.model;

public class Analysis {
    private int analysisID;
    private int userID;
    private String startDate;
    private String endDate;
    private double totalExpense;
    private Integer largestCategoryID;
    private double averageExpense;
    private String generatedAt;

    public Analysis() {
    }

    public Analysis(int analysisID, int userID, String startDate, String endDate, double totalExpense,
                    Integer largestCategoryID, double averageExpense, String generatedAt) {
        this.analysisID = analysisID;
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalExpense = totalExpense;
        this.largestCategoryID = largestCategoryID;
        this.averageExpense = averageExpense;
        this.generatedAt = generatedAt;
    }

    public int getAnalysisID() {
        return analysisID;
    }

    public void setAnalysisID(int analysisID) {
        this.analysisID = analysisID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Integer getLargestCategoryID() {
        return largestCategoryID;
    }

    public void setLargestCategoryID(Integer largestCategoryID) {
        this.largestCategoryID = largestCategoryID;
    }

    public double getAverageExpense() {
        return averageExpense;
    }

    public void setAverageExpense(double averageExpense) {
        this.averageExpense = averageExpense;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "analysisID=" + analysisID +
                ", userID=" + userID +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", totalExpense=" + totalExpense +
                ", largestCategoryID=" + largestCategoryID +
                ", averageExpense=" + averageExpense +
                ", generatedAt='" + generatedAt + '\'' +
                '}';
    }
}