package src.model;

public class EWallet extends AccountWallet {
    private String providerName;
    private String accountNumber;

    public EWallet() {
        super();
    }

    public EWallet(int accountID, int userID, String accountName, double balance, String providerName, String accountNumber) {
        super(accountID, userID, accountName, balance);
        this.providerName = providerName;
        this.accountNumber = accountNumber;
    }

    @Override
    public double calculateBalance() {
        return getBalance();
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "EWallet{" +
                "accountID=" + getAccountID() +
                ", userID=" + getUserID() +
                ", accountName='" + getAccountName() + '\'' +
                ", balance=" + getBalance() +
                ", providerName='" + providerName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}