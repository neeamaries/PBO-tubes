package src.model;

public class AccountWallet {
    private int accountID;
    private int userID;
    private String accountName;
    private double balance;

    public AccountWallet() {
    }

    public AccountWallet(int accountID, int userID, String accountName, double balance) {
        this.accountID = accountID;
        this.userID = userID;
        this.accountName = accountName;
        this.balance = balance;
    }

    public double calculateBalance() {
        return balance;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountWallet{" +
                "accountID=" + accountID +
                ", userID=" + userID +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}