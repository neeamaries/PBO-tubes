package src.model;

public class AccountWallet {
    protected int accountID;
    private String accountName;
    private double balance;

    public AccountWallet() {
    }

    public AccountWallet(int accountID, String accountName, double balance) {
        this.accountID = accountID;
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

    public void showWallet() {
        System.out.println("=== DETAIL DOMPET ===");
        System.out.println("ID Dompet   : " + accountID);
        System.out.println("Nama Dompet : " + accountName);
        System.out.println("Saldo       : " + balance);
    }

    @Override
    public String toString() {
        return "AccountWallet{" +
                "accountID=" + accountID +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
