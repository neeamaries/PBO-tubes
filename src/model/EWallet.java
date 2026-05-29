package src.model;

public class EWallet extends AccountWallet{
    private String providerName;

    public EWallet() {
        super();
    }

    public EWallet(int accountID, String accountName, double balance, String providerName) {
        super(accountID, accountName, balance);
        this.providerName = providerName;
    }

    public double calculateBalance(String providerName) {
        if (this.providerName.equalsIgnoreCase(providerName)) {
            return getBalance();
        }

        return 0;
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

    @Override
    public String toString() {
        return "EWallet{" +
                "accountID=" + getAccountID() +
                ", accountName='" + getAccountName() + '\'' +
                ", balance=" + getBalance() +
                ", providerName='" + providerName + '\'' +
                '}';
    }
}
