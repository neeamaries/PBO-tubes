package src.model;

public class PhysicalWallet extends AccountWallet{
    public PhysicalWallet() {
        super();
    }

    public PhysicalWallet(int accountID, String accountName, double balance) {
        super(accountID, accountName, balance);
    }

    @Override
    public double calculateBalance() {
        return getBalance();
    }

    @Override
    public String toString() {
        return "PhysicalWallet{" +
                "accountID=" + getAccountID() +
                ", accountName='" + getAccountName() + '\'' +
                ", balance=" + getBalance() +
                '}';
    }
}
