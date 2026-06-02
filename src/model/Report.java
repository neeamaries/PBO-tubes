package src.model;

public class Report {
    private int reportID;
    private int userID;
    private int accountID;
    private String reportType;
    private String startDate;
    private String endDate;
    private double totalIncome;
    private double totalExpense;
    private double endingBalance;
    private String generatedAt;

    public Report() {
    }

    public Report(int reportID, int userID, int accountID, String reportType,
                  String startDate, String endDate, double totalIncome,
                  double totalExpense, double endingBalance, String generatedAt) {
        this.reportID = reportID;
        this.userID = userID;
        this.accountID = accountID;
        this.reportType = reportType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.endingBalance = endingBalance;
        this.generatedAt = generatedAt;
    }

    public int getReportID() {
        return reportID;
    }

    public void setReportID(int reportID) {
        this.reportID = reportID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
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

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(double endingBalance) {
        this.endingBalance = endingBalance;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportID=" + reportID +
                ", userID=" + userID +
                ", accountID=" + accountID +
                ", reportType='" + reportType + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                ", endingBalance=" + endingBalance +
                ", generatedAt='" + generatedAt + '\'' +
                '}';
    }
}
