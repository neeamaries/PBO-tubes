package src.model;

public class Income extends Transaction {
     private int incomeID;

    public Income() {
        super();
    }

    public Income(int incomeID, int userID, int accountID, int transactionID, double amount, Category category, String date, String note) {
        super(userID, accountID, transactionID, amount, category, date, note);
        this.incomeID = incomeID;
    }

    public String getDetails() {
        return "Income ID: " + incomeID +
                ", Transaction ID: " + transactionID +
                ", Amount: " + amount +
                ", Category: " + getCategoryName() +
                ", Date: " + date +
                ", Note: " + note;
    }

   @Override
    public void execute(AccountWallet wallet) {
    if (wallet != null) {
        wallet.updateBalance(amount);
    }
}

    @Override
    public void rollback(AccountWallet wallet) {
    if (wallet != null) {
        wallet.updateBalance(-amount);
    }
    }

    public int getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(int incomeID) {
        this.incomeID = incomeID;
    }

    @Override
    public String toString() {
        return getDetails();
    }
}
