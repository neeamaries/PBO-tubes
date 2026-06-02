package src.model;

public class Transaction {
    private int transactionID;
    private int userID;
    private int accountID;
    private int categoryID;
    private String transactionName;
    private double amount;
    private String transactionType;
    private String transactionDate;
    private String note;

    private Category category;

    public Transaction() {
    }

    public Transaction(int transactionID, int userID, int accountID, int categoryID,
                       String transactionName, double amount, String transactionType,
                       String transactionDate, String note) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.accountID = accountID;
        this.categoryID = categoryID;
        this.transactionName = transactionName;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.note = note;
    }

    public Transaction(int transactionID, int userID, int accountID, int categoryID,
                       String transactionName, double amount, String transactionType,
                       String transactionDate, String note, Category category) {
        this.transactionID = transactionID;
        this.userID = userID;
        this.accountID = accountID;
        this.categoryID = categoryID;
        this.transactionName = transactionName;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.note = note;
        this.category = category;
    }

    public String getCategoryName() {
        if (category != null) {
            return category.getName();
        }
        return "Tidak ada kategori";
    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;

        if (category != null) {
            this.categoryID = category.getCategoryID();
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionID=" + transactionID +
                ", userID=" + userID +
                ", accountID=" + accountID +
                ", categoryID=" + categoryID +
                ", transactionName='" + transactionName + '\'' +
                ", amount=" + amount +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", note='" + note + '\'' +
                ", category=" + getCategoryName() +
                '}';
    }
}